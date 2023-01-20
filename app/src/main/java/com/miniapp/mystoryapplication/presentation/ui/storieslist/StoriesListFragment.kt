package com.miniapp.mystoryapplication.presentation.ui.storieslist

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.abstraction.OnItemClickListener
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.databinding.FragmentStoriesListBinding
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel
import com.miniapp.mystoryapplication.presentation.ui.storieslist.adapter.LoadingStateAdapter
import com.miniapp.mystoryapplication.presentation.ui.storieslist.adapter.StoryListPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoriesListFragment : BaseFragment(R.layout.fragment_stories_list), OnItemClickListener {
    private val binding by viewBinding(FragmentStoriesListBinding::bind)
    private val vm: StoriesListViewModel by viewModel()
    private val pagingAdapter: StoryListPagingAdapter by lazy { StoryListPagingAdapter(this) }

    private var isSavedInstanceState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isSavedInstanceState = savedInstanceState === null

        setupView()
    }

    override fun onResume() {
        super.onResume()
        if (isSavedInstanceState) getStories()
    }

    private fun getStories() {
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.containerLoadingProgress.root.isVisible =
                    loadStates.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launch {
            vm.getStoriesList(pageSize = PAGE_SIZE).collectLatest {
                pagingAdapter.submitData(it.map { story ->
                    StoriesResponseUiModel(
                        id = story.id,
                        name = story.name,
                        description = story.description,
                        photoUrl = story.photoUrl,
                        createdAt = story.createdAt,
                        lat = story.lat,
                        lon = story.lon
                    )
                })
            }
        }
    }

    private fun setupView() {
        binding.rvStoriesList.adapter =
            pagingAdapter.apply {
                withLoadStateFooter(footer = LoadingStateAdapter { pagingAdapter.retry() })
                addLoadStateListener { handleEmptyState() }
            }
    }

    private fun handleEmptyState() {
        if (pagingAdapter.itemCount == 0) {
            binding.containerEmptyData.root.visibility = View.VISIBLE
        } else {
            binding.containerEmptyData.root.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mapView -> {
                findNavController().navigate(
                    StoriesListFragmentDirections.toStoriesMapFragment()
                )
            }
        }
        return true
    }

    override fun onClick(item: Parcelable, view: View) {
        val extras = FragmentNavigatorExtras(view to "story_image")
        findNavController().navigate(
            StoriesListFragmentDirections.actionStoriesListFragmentToStoryDetailFragment()
                .setItem(item as StoriesResponseUiModel), extras
        )
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}