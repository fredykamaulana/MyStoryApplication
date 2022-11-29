package com.miniapp.mystoryapplication.data.repository

import com.miniapp.mystoryapplication.data.source.PreferencesDataSource
import com.miniapp.mystoryapplication.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val source: PreferencesDataSource) :
    PreferencesRepository {
    override fun loggedInUserName(username: String) = source.loggedInUserName(username)

    override fun loggedInUserName(): String = source.loggedInUserName()

    override fun loggedInUserId(userId: String) = source.loggedInUserId(userId)

    override fun loggedInUserId(): String = source.loggedInUserId()

    override fun loggedInToken(token: String) = source.loggedInToken(token)

    override fun loggedInToken(): String = source.loggedInToken()

    override fun logout() {
        source.logout()
    }
}