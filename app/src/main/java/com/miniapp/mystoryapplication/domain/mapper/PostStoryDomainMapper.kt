package com.miniapp.mystoryapplication.domain.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.data.request.PostStoryRequestDto
import com.miniapp.mystoryapplication.domain.requestdomain.PostStoryRequestDomainModel
import com.miniapp.mystoryapplication.presentation.requestui.PostStoryRequestUiModel

val mapPostStoryRequestUiToDomain =
    object : BaseMapper<PostStoryRequestUiModel, PostStoryRequestDomainModel>() {
        override fun map(data: PostStoryRequestUiModel): PostStoryRequestDomainModel {
            return PostStoryRequestDomainModel(
                data.imgFile,
                data.description
            )
        }
    }

val mapPostStoryRequestDomainToDto =
    object : BaseMapper<PostStoryRequestDomainModel, PostStoryRequestDto>() {
        override fun map(data: PostStoryRequestDomainModel): PostStoryRequestDto {
            return PostStoryRequestDto(
                data.imgFile,
                data.description
            )
        }
    }