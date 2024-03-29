package com.miniapp.mystoryapplication.data.source

import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.data.service.GetStoriesApiService
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow

class GetStoriesRemoteDataSource(private val apiService: GetStoriesApiService) : SafeApiCall() {
    suspend fun getStories(): Flow<RemoteResult<StoriesResponseDto>> {
        return safeApiCall { apiService.getStories() }
    }

    suspend fun getStoriesWithPaging(
        page: Int = 0,
        size: Int = 0
    ): Flow<RemoteResult<StoriesResponseDto>> {
        return safeApiCall { apiService.getStories(page = page, size = size) }
    }
}