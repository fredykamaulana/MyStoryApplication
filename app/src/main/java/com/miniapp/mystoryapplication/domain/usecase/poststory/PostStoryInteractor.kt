package com.miniapp.mystoryapplication.domain.usecase.poststory

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.PostStoryRepository
import com.miniapp.mystoryapplication.domain.requestdomain.PostStoryRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

class PostStoryInteractor(private val repository: PostStoryRepository) : PostStoryUseCase {
    override suspend fun postStory(postStoryRequest: PostStoryRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>> {
        return repository.postStory(postStoryRequest)
    }
}