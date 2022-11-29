package com.miniapp.mystoryapplication.domain.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel

val mapStoriesDomainToUi =
    object : BaseMapper<StoriesResponseDomainModel.ListStoryItem?, StoriesResponseUiModel>() {
        override fun map(data: StoriesResponseDomainModel.ListStoryItem?): StoriesResponseUiModel {
            return StoriesResponseUiModel(
                id = data?.id ?: "",
                name = data?.name ?: "",
                description = data?.description ?: "",
                photoUrl = data?.photoUrl ?: "",
                createdAt = data?.createdAt ?: ""
            )
        }
    }