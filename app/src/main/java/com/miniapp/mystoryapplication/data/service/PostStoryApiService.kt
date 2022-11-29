package com.miniapp.mystoryapplication.data.service

import com.miniapp.mystoryapplication.data.response.BaseResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostStoryApiService {
    /**
    URL=/stories
    Method=POST
    Headers={
    Content-Type: multipart/form-data
    Authorization: Bearer <token>
    }
    Request Body={
    description as string
    photo as file, must be a valid image file, max size 1MB
    lat as float, optional
    lon as float, optional
    }
    */
    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): BaseResponseDto
}