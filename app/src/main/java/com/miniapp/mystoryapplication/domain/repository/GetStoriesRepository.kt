package com.miniapp.mystoryapplication.domain.repository

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface GetStoriesRepository {
    suspend fun getStories(): Flow<ResultState<StoriesResponseDomainModel>>
}