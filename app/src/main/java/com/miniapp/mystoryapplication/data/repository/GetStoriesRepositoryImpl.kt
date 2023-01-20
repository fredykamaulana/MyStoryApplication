package com.miniapp.mystoryapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.miniapp.mystoryapplication.data.mapper.mapStoriesDtoToDomain
import com.miniapp.mystoryapplication.data.response.StoriesResponseDto
import com.miniapp.mystoryapplication.data.source.GetStoriesPagingSource
import com.miniapp.mystoryapplication.data.source.GetStoriesRemoteDataSource
import com.miniapp.mystoryapplication.data.utils.DataTransformer
import com.miniapp.mystoryapplication.data.utils.RemoteResult
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.repository.GetStoriesRepository
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getStoriesWithPaging(pageSize: Int): Flow<PagingData<StoriesResponseDomainModel.ListStoryItem>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, initialLoadSize = pageSize * 2),
            pagingSourceFactory = {
                GetStoriesPagingSource(getStoriesRemoteDataSource = remoteDataSource)
            }
        )
            .flow
            .map {
                it.map { stories ->
                    StoriesResponseDomainModel.ListStoryItem(
                        id = stories.id.orEmpty(),
                        name = stories.name.orEmpty(),
                        description = stories.description.orEmpty(),
                        photoUrl = stories.photoUrl.orEmpty(),
                        createdAt = stories.createdAt.orEmpty(),
                        lat = stories.lat ?: 0.0f,
                        lon = stories.lon ?: 0.0f
                    )
                }
            }
    }
}