package com.miniapp.mystoryapplication.domain.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import com.miniapp.mystoryapplication.presentation.responseui.StoriesResponseUiModel

val mapStoriesDomainToUi =
    object : BaseMapper<StoriesResponseDomainModel.ListStoryItem?, StoriesResponseUiModel>() {
        override fun map(data: StoriesResponseDomainModel.ListStoryItem?): StoriesResponseUiModel {
            return StoriesResponseUiModel(
                id = data?.id.orEmpty(),
                name = data?.name.orEmpty(),
                description = data?.description.orEmpty(),
                photoUrl = data?.photoUrl.orEmpty(),
                createdAt = data?.createdAt.orEmpty(),
                lon = data?.lon ?: 0.0f,
                lat = data?.lat ?: 0.0f
            )
        }
    }