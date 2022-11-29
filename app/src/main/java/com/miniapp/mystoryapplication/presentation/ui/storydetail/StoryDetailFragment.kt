package com.miniapp.mystoryapplication.presentation.ui.storydetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.core.abstraction.BaseFragment
import com.miniapp.mystoryapplication.core.delegates.viewBinding
import com.miniapp.mystoryapplication.databinding.FragmentStoryDetailBinding
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel
import com.miniapp.mystoryapplication.presentation.utils.loadImage

class StoryDetailFragment : BaseFragment(R.layout.fragment_story_detail) {
    private val binding by viewBinding(FragmentStoryDetailBinding::bind)
    private val args: StoryDetailFragmentArgs by navArgs()
    private val item: StoriesResponseUiModel? by lazy { args.item }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        val dd = item?.createdAt?.substring(8..9)
        val mm = item?.createdAt?.substring(5..6)
        val yyy = item?.createdAt?.substring(0..3)

        binding.ivStoriesItemImage.loadImage(item?.photoUrl ?: "")
        binding.tvStoriesItemName.text = item?.name ?: ""
        binding.tvStoriesItemDate.text = "$dd-$mm-$yyy"
        binding.tvStoriesItemDesc.text = item?.description ?: ""

        binding.root.invalidate()
    }
}