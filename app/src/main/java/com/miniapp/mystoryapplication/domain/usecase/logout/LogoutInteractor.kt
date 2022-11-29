package com.miniapp.mystoryapplication.domain.usecase.logout

import com.miniapp.mystoryapplication.domain.repository.PreferencesRepository

class LogoutInteractor(private val repository: PreferencesRepository) : LogoutUseCase {
    override fun logout() {
        repository.logout()
    }
}