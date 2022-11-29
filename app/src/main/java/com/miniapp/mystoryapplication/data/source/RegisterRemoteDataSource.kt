package com.miniapp.mystoryapplication.data.source

import com.miniapp.mystoryapplication.data.request.RegisterRequestDto
import com.miniapp.mystoryapplication.data.response.BaseResponseDto
import com.miniapp.mystoryapplication.data.service.RegisterApiService
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow

class RegisterRemoteDataSource(private val apiService: RegisterApiService) : SafeApiCall() {
    suspend fun register(registerRequestDto: RegisterRequestDto): Flow<RemoteResult<BaseResponseDto>> {
        return safeApiCall { apiService.register(registerRequestDto) }
    }
}