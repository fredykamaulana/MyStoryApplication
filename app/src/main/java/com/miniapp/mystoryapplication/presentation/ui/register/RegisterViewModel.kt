package com.miniapp.mystoryapplication.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.mapper.mapRegisterRequestUiToDomain
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import com.miniapp.mystoryapplication.domain.usecase.register.RegisterUseCases
import com.miniapp.mystoryapplication.presentation.requestui.RegisterRequestUiModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCases: RegisterUseCases) : ViewModel() {
    private val _registerResult = MutableLiveData<ResultState<BaseResponseDomainModel>>()
    val registerResult: LiveData<ResultState<BaseResponseDomainModel>> get() = _registerResult

    fun register(requestUiModel: RegisterRequestUiModel) {
        _registerResult.value = ResultState.Loading()
        viewModelScope.launch {
            _registerResult.value =
                registerUseCases.register(mapRegisterRequestUiToDomain.map(requestUiModel)).last()
        }
    }
}