package com.miniapp.mystoryapplication.domain.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.domain.requestdomain.LoginRequestDomainModel
import com.miniapp.mystoryapplication.presentation.requestui.LoginRequestUiModel

val mapLoginRequestUiToDomain =
    object : BaseMapper<LoginRequestUiModel, LoginRequestDomainModel>() {
        override fun map(data: LoginRequestUiModel): LoginRequestDomainModel {
            return LoginRequestDomainModel(
                username = data.username,
                password = data.password
            )
        }
    }

