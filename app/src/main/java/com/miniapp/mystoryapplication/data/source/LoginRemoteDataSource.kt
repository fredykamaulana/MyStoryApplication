package com.miniapp.mystoryapplication.data.source

import com.miniapp.mystoryapplication.data.request.LoginRequestDto
import com.miniapp.mystoryapplication.data.response.LoginResponseDto
import com.miniapp.mystoryapplication.data.service.LoginApiService
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.SafeApiCall
import kotlinx.coroutines.flow.Flow

class LoginRemoteDataSource(private val apiService: LoginApiService) : SafeApiCall() {
    suspend fun login(loginRequestDto: LoginRequestDto): Flow<RemoteResult<LoginResponseDto>> {
        return safeApiCall { apiService.login(loginRequestDto) }
    }
}