package com.miniapp.mystoryapplication.data.service

import com.miniapp.mystoryapplication.data.request.LoginRequestDto
import com.miniapp.mystoryapplication.data.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): LoginResponseDto
}