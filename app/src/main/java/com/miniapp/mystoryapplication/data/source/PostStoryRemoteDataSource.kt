package com.miniapp.mystoryapplication.data.source

import com.miniapp.mystoryapplication.data.request.PostStoryRequestDto
import com.miniapp.mystoryapplication.data.response.BaseResponseDto
import com.miniapp.mystoryapplication.data.service.PostStoryApiService
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow

class PostStoryRemoteDataSource(private val apiService: PostStoryApiService) : SafeApiCall() {
    suspend fun postStory(postStoryRequestDto: PostStoryRequestDto): Flow<RemoteResult<BaseResponseDto>> {
        return safeApiCall {
            apiService.postStory(postStoryRequestDto.imgFile, postStoryRequestDto.description)
        }
    }
}