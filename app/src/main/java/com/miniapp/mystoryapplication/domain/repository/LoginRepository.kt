package com.miniapp.mystoryapplication.domain.repository

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.requestdomain.LoginRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.LoginResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginModel: LoginRequestDomainModel): Flow<ResultState<LoginResponseDomainModel>>
}