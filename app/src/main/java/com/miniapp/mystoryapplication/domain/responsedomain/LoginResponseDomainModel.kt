package com.miniapp.mystoryapplication.domain.responsedomain

data class LoginResponseDomainModel(
    val loginResult: LoginResult,
    val error: Boolean? = null,
    val message: String? = null
) {
    data class LoginResult(
        val name: String,
        val userId: String,
        val token: String
    )
}