package com.miniapp.mystoryapplication.domain.requestdomain

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class PostStoryRequestDomainModel(
    val imgFile: MultipartBody.Part,
    val description: RequestBody
)
