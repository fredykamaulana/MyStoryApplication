package com.miniapp.mystoryapplication.data.request

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class PostStoryRequestDto(
    val imgFile: MultipartBody.Part,
    val description: RequestBody
)
