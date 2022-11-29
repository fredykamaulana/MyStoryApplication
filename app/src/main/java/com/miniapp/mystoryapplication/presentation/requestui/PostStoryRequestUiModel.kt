package com.miniapp.mystoryapplication.presentation.requestui

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class PostStoryRequestUiModel(
    val imgFile: MultipartBody.Part,
    val description: RequestBody
)
