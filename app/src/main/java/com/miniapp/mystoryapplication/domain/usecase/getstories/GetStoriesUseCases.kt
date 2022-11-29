package com.miniapp.mystoryapplication.domain.usecase.getstories

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface GetStoriesUseCases {
    suspend fun getStories(): Flow<ResultState<StoriesResponseDomainModel>>
}