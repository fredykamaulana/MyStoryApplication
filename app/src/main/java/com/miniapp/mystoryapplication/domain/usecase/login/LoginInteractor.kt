package com.miniapp.mystoryapplication.domain.usecase.login

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.LoginRepository
import com.miniapp.mystoryapplication.domain.requestdomain.LoginRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.LoginResponseDomainModel
import kotlinx.coroutines.flow.Flow

class LoginInteractor(private val repository: LoginRepository) : LoginUseCases {
    override suspend fun login(loginModel: LoginRequestDomainModel): Flow<ResultState<LoginResponseDomainModel>> {
        return repository.login(loginModel)
    }
}