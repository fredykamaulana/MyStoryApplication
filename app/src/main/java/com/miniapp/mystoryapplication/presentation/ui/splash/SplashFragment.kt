package com.miniapp.mystoryapplication.presentation.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.databinding.FragmentSplashBinding
import com.miniapp.mystoryapplication.presentation.utils.setAnimatorAlpha
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val vm: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAnimation()
    }

    override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun setupAnimation() {
        val animeView =
            ObjectAnimator.ofFloat(binding.avSplashAnimation, View.TRANSLATION_Y, 500f, 30f)
                .setDuration(2000L)

        AnimatorSet().apply {
            playSequentially(animeView, binding.tvSplashLabel.setAnimatorAlpha())
            start()
        }
    }

    private fun setupView() {
        if (vm.getLoggedInToken().isNotEmpty()) navigateToStoriesListScreen()
        else navigateToLoginScreen()
    }

    private fun navigateToStoriesListScreen() {
        lifecycleScope.launch {
            delay(4000)
            findNavController().navigate(R.id.to_stories_list_fragment_new)
        }
    }

    private fun navigateToLoginScreen() {
        lifecycleScope.launch {
            val extras = FragmentNavigatorExtras(
                binding.avSplashAnimation to "image_view"
            )

            delay(4000)
            findNavController().navigate(
                R.id.to_login_fragment_no_transition,
                null,
                null,
                extras
            )
        }
    }
}