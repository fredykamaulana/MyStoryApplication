package com.miniapp.mystoryapplication.data.repository

import com.miniapp.mystoryapplication.data.response.BaseResponseDto
import com.miniapp.mystoryapplication.data.source.PostStoryRemoteDataSource
import com.miniapp.mystoryapplication.data.utils.DataTransformer
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.mapper.mapPostStoryRequestDomainToDto
import com.miniapp.mystoryapplication.domain.repository.PostStoryRepository
import com.miniapp.mystoryapplication.domain.requestdomain.PostStoryRequestDomainModel
import com.miniapp.mystoryapplication.domain.responsedomain.BaseResponseDomainModel
import kotlinx.coroutines.flow.Flow

class PostStoryRepositoryImpl(private val remoteDataSource: PostStoryRemoteDataSource) :
    PostStoryRepository {
    override suspend fun postStory(postStoryRequest: PostStoryRequestDomainModel): Flow<ResultState<BaseResponseDomainModel>> =
        object : DataTransformer<BaseResponseDto, BaseResponseDomainModel>() {
            override suspend fun fetchData(): Flow<RemoteResult<BaseResponseDto>> {
                return remoteDataSource.postStory(
                    mapPostStoryRequestDomainToDto.map(
                        postStoryRequest
                    )
                )
            }

            override fun transformData(data: BaseResponseDto?): BaseResponseDomainModel {
                return BaseResponseDomainModel(
                    error = data?.error ?: false,
                    message = data?.message ?: ""
                )
            }
        }.asFlow()
}