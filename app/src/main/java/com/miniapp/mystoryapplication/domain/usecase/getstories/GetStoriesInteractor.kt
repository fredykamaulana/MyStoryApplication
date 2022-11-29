package com.miniapp.mystoryapplication.domain.usecase.getstories

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.GetStoriesRepository
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import kotlinx.coroutines.flow.Flow

class GetStoriesInteractor(private val repository: GetStoriesRepository) : GetStoriesUseCases{
    override suspend fun getStories(): Flow<ResultState<StoriesResponseDomainModel>> =
        repository.getStories()
}