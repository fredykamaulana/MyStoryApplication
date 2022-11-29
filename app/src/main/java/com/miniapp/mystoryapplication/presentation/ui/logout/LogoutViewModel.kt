package com.miniapp.mystoryapplication.presentation.ui.logout

import androidx.lifecycle.ViewModel
import com.miniapp.mystoryapplication.domain.usecase.logout.LogoutUseCase

class LogoutViewModel(private val useCase: LogoutUseCase) : ViewModel() {
    fun logout() {
        useCase.logout()
    }
}