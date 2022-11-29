package com.miniapp.mystoryapplication.domain.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.domain.requestdomain.RegisterRequestDomainModel
import com.miniapp.mystoryapplication.presentation.requestui.RegisterRequestUiModel

val mapRegisterRequestUiToDomain =
    object : BaseMapper<RegisterRequestUiModel, RegisterRequestDomainModel>() {
        override fun map(data: RegisterRequestUiModel): RegisterRequestDomainModel {
            return RegisterRequestDomainModel(
                name = data.name,
                email = data.email,
                password = data.password
            )
        }
    }