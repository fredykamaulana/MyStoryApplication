package com.miniapp.mystoryapplication.domain.usecase.preferences

import com.miniapp.mystoryapplication.domain.repository.PreferencesRepository

class PreferencesInteractor(private val repository: PreferencesRepository) : PreferencesUseCases {
    override fun loggedInUserName(username: String) = repository.loggedInUserName(username)

    override fun loggedInUserName(): String = repository.loggedInUserName()

    override fun loggedInUserId(userId: String) = repository.loggedInUserId(userId)

    override fun loggedInUserId(): String = repository.loggedInUserId()

    override fun loggedInToken(token: String) = repository.loggedInToken(token)

    override fun loggedInToken(): String = repository.loggedInToken()

}