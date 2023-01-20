package com.miniapp.mystoryapplication.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import kotlinx.coroutines.flow.last

class GetStoriesPagingSource(private val getStoriesRemoteDataSource: GetStoriesRemoteDataSource) :
    PagingSource<Int, StoriesResponseDto.ListStoryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoriesResponseDto.ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = getStoriesRemoteDataSource.getStoriesWithPaging(
                page = position,
                size = params.loadSize
            ).last()

            when (responseData) {
                is RemoteResult.Success -> {
                    LoadResult.Page(
                        data = responseData.data.listStory,
                        prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                        nextKey = if (responseData.data.listStory.isEmpty()) null else position + 1
                    )
                }
                else -> {
                    LoadResult.Error(Exception())
                }
            }

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, StoriesResponseDto.ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}