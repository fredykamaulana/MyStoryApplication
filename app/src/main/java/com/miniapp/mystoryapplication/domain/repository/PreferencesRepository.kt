package com.miniapp.mystoryapplication.domain.repository

interface PreferencesRepository {
    fun loggedInUserName(username: String)

    fun loggedInUserName(): String

    fun loggedInUserId(userId: String)

    fun loggedInUserId(): String

    fun loggedInToken(token: String)

    fun loggedInToken(): String

    fun logout()
}