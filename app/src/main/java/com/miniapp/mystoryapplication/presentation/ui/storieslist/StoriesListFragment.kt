package com.miniapp.mystoryapplication.presentation.ui.storieslist

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.abstraction.OnItemClickListener
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.databinding.FragmentStoriesListBinding
import com.miniapp.mystoryapplication.domain.mapper.mapStoriesDomainToUi
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel
import com.miniapp.mystoryapplication.presentation.ui.storieslist.adapter.StoryListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoriesListFragment : BaseFragment(R.layout.fragment_stories_list), OnItemClickListener {
    private val binding by viewBinding(FragmentStoriesListBinding::bind)
    private val vm: StoriesListViewModel by viewModel()

    private var adapter: StoryListAdapter? = null
    private var isSavedInstanceState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StoryListAdapter(this)
        isSavedInstanceState = savedInstanceState === null

        setupObservers()
        setupView()
    }

    override fun onResume() {
        super.onResume()
        if (isSavedInstanceState) vm.getStoriesList()
    }

    private fun setupObservers() {
        vm.getStoriesList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is ResultState.Loading -> {
                    binding.containerLoadingProgress.root.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    val storiesList = data.data?.listStory?.map {
                        mapStoriesDomainToUi.map(it)
                    }
                    if (storiesList?.isNotEmpty() == true) {
                        adapter?.submitList(storiesList)
                        binding.rvStoriesList.visibility = View.VISIBLE
                    } else {
                        binding.rvStoriesList.visibility = View.GONE
                        binding.containerEmptyData.root.visibility = View.VISIBLE
                    }
                }
                is ResultState.Error -> {
                    binding.containerLoadingProgress.root.visibility = View.GONE
                    showSnackBar(
                        binding.root, data.message ?: "", duration = Snackbar.LENGTH_LONG
                    ) { vm.getStoriesList() }
                }
            }
        }
    }

    private fun setupView() {
        binding.rvStoriesList.adapter = adapter
    }

    override fun onClick(item: Parcelable, view: View) {
        val extras = FragmentNavigatorExtras(view to "story_image")
        findNavController().navigate(
            StoriesListFragmentDirections.actionStoriesListFragmentToStoryDetailFragment()
                .setItem(item as StoriesResponseUiModel),
            extras
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}