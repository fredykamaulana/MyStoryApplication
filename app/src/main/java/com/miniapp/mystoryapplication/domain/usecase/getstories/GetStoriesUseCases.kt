package com.miniapp.mystoryapplication.domain.usecase.getstories

import androidx.paging.PagingData
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface GetStoriesUseCases {
    suspend fun getStories(): Flow<ResultState<StoriesResponseDomainModel>>

    fun getStoriesWithPaging(pageSize: Int = 0): Flow<PagingData<StoriesResponseDomainModel.ListStoryItem>>
}