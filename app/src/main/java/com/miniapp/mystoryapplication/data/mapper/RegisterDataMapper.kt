package com.miniapp.mystoryapplication.data.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.data.request.RegisterRequestDto
import com.miniapp.mystoryapplication.domain.requestdomain.RegisterRequestDomainModel

val mapRegisterRequestDomainToDto =
    object : BaseMapper<RegisterRequestDomainModel, RegisterRequestDto>() {
        override fun map(data: RegisterRequestDomainModel): RegisterRequestDto {
            return RegisterRequestDto(
                name = data.name,
                email = data.email,
                password = data.password
            )
        }
    }