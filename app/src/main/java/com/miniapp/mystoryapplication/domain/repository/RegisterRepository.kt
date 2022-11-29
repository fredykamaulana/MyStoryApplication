package com.miniapp.mystoryapplication.domain.repository

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.requestdomain.RegisterRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerModel: RegisterRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>>
}