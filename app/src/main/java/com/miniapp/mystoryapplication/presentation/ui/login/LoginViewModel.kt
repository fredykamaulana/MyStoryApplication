package com.miniapp.mystoryapplication.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.mapper.mapLoginRequestUiToDomain
import com.miniapp.mystoryapplication.domain.responsedomain.LoginResponseDomainModel
import com.miniapp.mystoryapplication.domain.usecase.login.LoginUseCases
import com.miniapp.mystoryapplication.domain.usecase.preferences.PreferencesUseCases
import com.miniapp.mystoryapplication.presentation.requestui.LoginRequestUiModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCases: LoginUseCases,
    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {
    private val _loginResult = MutableLiveData<ResultState<LoginResponseDomainModel>>()
    val loginResult: LiveData<ResultState<LoginResponseDomainModel>> get() = _loginResult

    fun login(request: LoginRequestUiModel) {
        _loginResult.value = ResultState.Loading()
        viewModelScope.launch {
            _loginResult.value = loginUseCases.login(mapLoginRequestUiToDomain.map(request)).last()
        }
    }

    fun saveUserId(userId: String) = preferencesUseCases.loggedInUserId(userId)
    fun saveUserName(userName: String) = preferencesUseCases.loggedInUserName(userName)
    fun saveToken(token: String) = preferencesUseCases.loggedInToken(token)
}