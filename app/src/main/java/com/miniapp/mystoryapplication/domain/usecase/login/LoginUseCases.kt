package com.miniapp.mystoryapplication.domain.usecase.login

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.requestdomain.LoginRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.LoginResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface LoginUseCases {
    suspend fun login(loginModel: LoginRequestDomainModel): Flow<ResultState<LoginResponseDomainModel>>
}