package com.miniapp.mystoryapplication.data.repository

import com.miniapp.mystoryapplication.data.mapper.mapStoriesDtoToDomain
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.data.source.GetStoriesRemoteDataSource
import com.miniapp.mystoryapplication.data.utils.DataTransformer
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.GetStoriesRepository
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import kotlinx.coroutines.flow.Flow

class GetStoriesRepositoryImpl(private val remoteDataSource: GetStoriesRemoteDataSource) :
    GetStoriesRepository {
    override suspend fun getStories(): Flow<ResultState<StoriesResponseDomainModel>> =
        object : DataTransformer<StoriesResponseDto, StoriesResponseDomainModel>() {
            override suspend fun fetchData(): Flow<RemoteResult<StoriesResponseDto>> {
                return remoteDataSource.getStories()
            }

            override fun transformData(data: StoriesResponseDto?): StoriesResponseDomainModel {
                return StoriesResponseDomainModel(
                    error = data?.error ?: false,
                    message = data?.message ?: "",
                    listStory = data?.listStory?.map { mapStoriesDtoToDomain.map(it) } ?: listOf()
                )
            }
        }.asFlow()
}