package com.miniapp.mystoryapplication.presentation.ui.storieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miniapp.mystoryapplication.core.abstraction.OnItemClickListener
import com.miniapp.mystoryapplication.databinding.LayoutStoriesItemBinding
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel
import com.miniapp.mystoryapplication.presentation.utils.loadImage

class StoryListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<StoriesResponseUiModel, StoryListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.create(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], listener)
    }

    class ViewHolder(private val binding: LayoutStoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoriesResponseUiModel, listener: OnItemClickListener) {
            binding.ivStoriesItemImage.loadImage(item.photoUrl)
            binding.tvStoriesItemName.text = item.name
            binding.tvStoriesItemDesc.text = item.description
            binding.cvStoriesItemContainer.setOnClickListener {
                listener.onClick(item = item, binding.ivStoriesItemImage)
            }

            itemView.invalidate()
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = LayoutStoriesItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<StoriesResponseUiModel>() {
            override fun areItemsTheSame(
                oldItem: StoriesResponseUiModel, newItem: StoriesResponseUiModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: StoriesResponseUiModel, newItem: StoriesResponseUiModel
            ): Boolean = oldItem == newItem
        }
    }
}