package com.miniapp.mystoryapplication.data.request

import com.squareup.moshi.Json


data class LoginRequestDto(
    @field:Json(name = "email")
    val username: String,
    @field:Json(name = "password")
    val password: String
)
