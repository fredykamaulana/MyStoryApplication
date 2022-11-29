package com.miniapp.mystoryapplication.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class DataTransformer<ResponseType, OutputType> {
    private var result: Flow<ResultState<OutputType>> = flow {

        try {
            emit(ResultState.Loading())

            when (val response = fetchData().first()) {
                is RemoteResult.Success -> {
                    emit(ResultState.Success(transformData(response.data)))
                }
                is RemoteResult.Empty -> {
                    emit(ResultState.Success(transformData(null)))
                }
                is RemoteResult.Error -> {
                    emit(ResultState.Error(response.errorMessage, response.code))
                }
            }
        } catch (e: Exception){
            emit(ResultState.Error(e.message))
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun fetchData(): Flow<RemoteResult<ResponseType>>

    protected abstract fun transformData(data: ResponseType?): OutputType

    fun asFlow(): Flow<ResultState<OutputType>> = result
}