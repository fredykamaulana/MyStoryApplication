package com.miniapp.mystoryapplication.domain.usecase.register

import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.RegisterRepository
import com.miniapp.mystoryapplication.domain.requestdomain.RegisterRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

class RegisterInteractor(private val repository: RegisterRepository) : RegisterUseCases {
    override suspend fun register(registerModel: RegisterRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>> {
        return repository.register(registerModel)
    }
}