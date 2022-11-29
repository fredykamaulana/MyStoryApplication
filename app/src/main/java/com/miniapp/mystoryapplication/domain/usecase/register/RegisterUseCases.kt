package com.miniapp.mystoryapplication.domain.usecase.register

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.requestdomain.RegisterRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface RegisterUseCases {
    suspend fun register(registerModel: RegisterRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>>
}