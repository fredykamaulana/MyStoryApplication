package com.miniapp.mystoryapplication.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.miniapp.mystoryapplication.domain.usecase.preferences.PreferencesUseCases

class SplashViewModel(private val preferencesUseCases: PreferencesUseCases) : ViewModel() {
    fun getLoggedInToken() = preferencesUseCases.loggedInToken()
}