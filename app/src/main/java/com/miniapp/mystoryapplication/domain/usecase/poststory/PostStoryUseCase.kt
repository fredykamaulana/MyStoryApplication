package com.miniapp.mystoryapplication.domain.usecase.poststory

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.requestdomain.PostStoryRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface PostStoryUseCase {
    suspend fun postStory(postStoryRequest: PostStoryRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>>
}