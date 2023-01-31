package com.miniapp.mystoryapplication.presentation.ui.storieslist.utils

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto

class GetStoriesPagingSourceTest : PagingSource<Int, StoriesResponseDto.ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, StoriesResponseDto.ListStoryItem>): Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoriesResponseDto.ListStoryItem> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

    companion object {
        fun snapshot(items: List<StoriesResponseDto.ListStoryItem>): PagingData<StoriesResponseDto.ListStoryItem> {
            return PagingData.from(items)
        }
    }
}