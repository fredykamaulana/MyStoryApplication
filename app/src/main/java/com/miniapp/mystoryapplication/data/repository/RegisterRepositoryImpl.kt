package com.miniapp.mystoryapplication.data.repository

import com.miniapp.mystoryapplication.data.mapper.mapRegisterRequestDomainToDto
import com.miniapp.mystoryapplication.data.response.BaseResponseDto
import com.miniapp.mystoryapplication.data.source.RegisterRemoteDataSource
import com.miniapp.mystoryapplication.data.utils.DataTransformer
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.RegisterRepository
import com.miniapp.mystoryapplication.domain.requestdomain.RegisterRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

class RegisterRepositoryImpl(private val remoteDataSource: RegisterRemoteDataSource) :
    RegisterRepository {
    override suspend fun register(registerModel: RegisterRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>> =
        object : DataTransformer<BaseResponseDto, BaseResponseDomainModel>() {
            override suspend fun fetchData(): Flow<RemoteResult<BaseResponseDto>> {
                return remoteDataSource.register(mapRegisterRequestDomainToDto.map(registerModel))
            }

            override fun transformData(data: BaseResponseDto?): BaseResponseDomainModel {
                return BaseResponseDomainModel(
                    error = data?.error ?: false,
                    message = data?.message ?: ""
                )
            }
        }.asFlow()
}