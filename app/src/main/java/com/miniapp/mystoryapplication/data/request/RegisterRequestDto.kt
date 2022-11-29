package com.miniapp.mystoryapplication.data.request

import com.squareup.moshi.Json

data class RegisterRequestDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "password")
    val password: String
)
