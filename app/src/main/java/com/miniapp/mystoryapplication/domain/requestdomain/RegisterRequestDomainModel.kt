package com.miniapp.mystoryapplication.domain.requestdomain

data class RegisterRequestDomainModel(
    val name: String,
    val email: String,
    val password: String
)