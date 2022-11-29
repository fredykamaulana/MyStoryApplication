package com.miniapp.mystoryapplication.data.service

import com.miniapp.mystoryapplication.data.request.RegisterRequestDto
import com.miniapp.mystoryapplication.data.response.BaseResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("register")
    suspend fun register(
        @Body registerRequestDto: RegisterRequestDto
    ): BaseResponseDto
}