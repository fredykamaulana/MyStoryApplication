package com.miniapp.mystoryapplication.data.mapper

import com.miniapp.mystoryapplication.core.abstraction.BaseMapper
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel

val mapStoriesDtoToDomain =
    object : BaseMapper<StoriesResponseDto.ListStoryItem?, StoriesResponseDomainModel.ListStoryItem>() {
        override fun map(data: StoriesResponseDto.ListStoryItem?): StoriesResponseDomainModel.ListStoryItem {
            return StoriesResponseDomainModel.ListStoryItem(
                id = data?.id ?: "",
                name = data?.name ?: "",
                description = data?.description ?: "",
                photoUrl = data?.photoUrl ?: "",
                createdAt = data?.createdAt ?: "",
                lat = data?.lat ?: 0F,
                lon = data?.lon ?: 0F
            )
        }
    }