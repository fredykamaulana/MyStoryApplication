package com.miniapp.mystoryapplication.domain.usecase.preferences

interface PreferencesUseCases {
    fun loggedInUserName(username: String)

    fun loggedInUserName(): String

    fun loggedInUserId(userId: String)

    fun loggedInUserId(): String

    fun loggedInToken(token: String)

    fun loggedInToken(): String
}