package com.miniapp.mystoryapplication.data.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.data.request.LoginRequestDto
import com.miniapp.mystoryapplication.data.response.LoginResponseDto
import com.miniapp.mystoryapplication.domain.requestdomain.LoginRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.LoginResponseDomainModel

val mapLoginDtoToDomainModel =
    object : BaseMapper<LoginResponseDto.LoginResult?, LoginResponseDomainModel.LoginResult>() {
        override fun map(data: LoginResponseDto.LoginResult?): LoginResponseDomainModel.LoginResult {
            return LoginResponseDomainModel.LoginResult(
                name = data?.name ?: "",
                userId = data?.userId ?: "",
                token = data?.token ?: ""
            )
        }
    }

val mapLoginRequestDomainToDto =
    object : BaseMapper<LoginRequestDomainModel, LoginRequestDto>() {
        override fun map(data: LoginRequestDomainModel): LoginRequestDto {
            return LoginRequestDto(
                username = data.username,
                password = data.password
            )
        }
    }