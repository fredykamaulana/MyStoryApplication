package com.miniapp.mystoryapplication.data.response

import com.squareup.moshi.Json

data class LoginResponseDto(

    @field:Json(name = "loginResult")
    val loginResult: LoginResult? = null,

    @field:Json(name = "error")
    val error: Boolean? = null,

    @field:Json(name = "message")
    val message: String? = null
) {
    data class LoginResult(

        @field:Json(name = "name")
        val name: String? = null,

        @field:Json(name = "userId")
        val userId: String? = null,

        @field:Json(name = "token")
        val token: String? = null
    )
}
