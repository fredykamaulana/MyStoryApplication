package com.miniapp.mystoryapplication.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseActivity
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.databinding.ActivityMainBinding
import com.miniapp.mystoryapplication.presentation.ui.logout.LogoutFragment
import com.miniapp.mystoryapplication.presentation.utils.setupToolbar

class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var toolbar: Toolbar

    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration.Builder(
            R.id.stories_list_fragment,
            R.id.story_detail_fragment
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        toolbar = binding.toolbar
        toolbar.setupWithNavController(navController, appBarConfiguration)

        setupNavigation()
        setupOnBackPressed()
        setupView()
    }

    private fun setupNavigation() {
        binding.bottomNavigation.apply {
            background = null
            menu.getItem(0).isEnabled = false
            menu.getItem(1).isEnabled = false
            menu.getItem(2).isEnabled = true
            setOnItemSelectedListener {
                if (it.itemId == R.id.logout) {
                    val dialog = LogoutFragment()
                    dialog.show(supportFragmentManager, dialog.tag)
                }
                true
            }
        }

        navController.addOnDestinationChangedListener(navigationListener)
    }

    private val navigationListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splash_fragment, R.id.login_fragment, R.id.register_fragment, R.id.camera_x_fragment -> {
                    setWindowToFullScreen()
                }
                R.id.stories_list_fragment -> {
                    setWindowWithToolbar(resources.getString(R.string.stories_list_title_page))
                    setScreeWithBottomNav()
                }
                R.id.story_detail_fragment -> {
                    setWindowWithToolbar(resources.getString(R.string.story_detail_title_page))
                    setScreeWithoutBottomNav()
                }
                R.id.new_story_fragment -> {
                    setWindowWithToolbar(resources.getString(R.string.new_story_title_page))
                    setScreeWithoutBottomNav()
                }
                R.id.stories_map_fragment -> {
                    setScreeWithoutBottomNav()
                }
            }
        }

    private fun setupOnBackPressed() {
        val onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment = navController.currentDestination
                when (currentFragment?.id) {
                    R.id.login_fragment, R.id.stories_list_fragment -> finish()
                    else -> navController.navigateUp()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    private fun setWindowToFullScreen() {
        binding.appBar.visibility = View.GONE
        setScreeWithoutBottomNav()
    }

    private fun setWindowWithToolbar(title: String) {
        binding.appBar.visibility = View.VISIBLE
        setupToolbar(binding.toolbar, title)
    }

    private fun setScreeWithoutBottomNav() {
        binding.bottomAppbar.visibility = View.GONE
        binding.fabNewStories.visibility = View.GONE
    }

    private fun setScreeWithBottomNav() {
        binding.bottomAppbar.visibility = View.VISIBLE
        binding.fabNewStories.visibility = View.VISIBLE
    }

    private fun setupView() {
        binding.fabNewStories.setOnClickListener {
            navController.navigate(R.id.action_stories_list_fragment_to_new_story_fragment)
        }
    }
}