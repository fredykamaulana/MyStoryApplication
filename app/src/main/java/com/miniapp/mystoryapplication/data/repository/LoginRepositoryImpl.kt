package com.miniapp.mystoryapplication.data.repository

import com.miniapp.mystoryapplication.data.mapper.mapLoginDtoToDomainModel
import com.miniapp.mystoryapplication.data.mapper.mapLoginRequestDomainToDto
import com.miniapp.mystoryapplication.data.response.LoginResponseDto
import com.miniapp.mystoryapplication.data.source.LoginRemoteDataSource
import com.miniapp.mystoryapplication.data.utils.DataTransformer
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.LoginRepository
import com.miniapp.mystoryapplication.domain.requestdomain.LoginRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.LoginResponseDomainModel
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(private val remoteDataSource: LoginRemoteDataSource) : LoginRepository {
    override suspend fun login(loginModel: LoginRequestDomainModel): Flow<ResultState<LoginResponseDomainModel>> =
        object : DataTransformer<LoginResponseDto, LoginResponseDomainModel>() {
            override suspend fun fetchData(): Flow<RemoteResult<LoginResponseDto>> {
                return remoteDataSource.login(mapLoginRequestDomainToDto.map(loginModel))
            }

            override fun transformData(data: LoginResponseDto?): LoginResponseDomainModel {
                return LoginResponseDomainModel(
                    loginResult = mapLoginDtoToDomainModel.map(data?.loginResult),
                    error = data?.error,
                    message = data?.message
                )
            }
        }.asFlow()
}