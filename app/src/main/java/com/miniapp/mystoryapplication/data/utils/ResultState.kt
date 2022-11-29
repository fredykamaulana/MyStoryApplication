package com.miniapp.mystoryapplication.data.utils

sealed class ResultState<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: Int? = null,
) {
    class Loading<T>(data: T? = null) : ResultState<T>(data)
    class Success<T>(data: T) : ResultState<T>(data)
    class Error<T>(message: String? = null, errorCode: Int? = null, data: T? = null) :
        ResultState<T>(data, message, errorCode)
}