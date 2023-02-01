package com.miniapp.mystoryapplication.data.service

import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GetStoriesApiService {
    /**
    URL=/stories
    Parameters ={
    page as int, optional
    size as int, optional
    location as 1 | 0, optional, default 0
    Notes:
    1 for get all stories with location
    0 for all stories without considering location
    }
    Method=GET
    Headers ={
    Authorization: Bearer <token>
    }
     */

    @GET("stories")
    suspend fun getStories(
        @Query("location") location: Int = 1
    ): StoriesResponseDto

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 0,
        @Query("location") location: Int = 0
    ): StoriesResponseDto
}