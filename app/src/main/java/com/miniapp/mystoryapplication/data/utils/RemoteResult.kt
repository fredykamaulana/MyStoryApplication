package com.miniapp.mystoryapplication.data.utils

sealed class RemoteResult<out R> {
    data class Success<out T>(val data: T) : RemoteResult<T>()
    data class Error(
        val cause: HttpState? = HttpState.NOT_DEFINED,
        val code: Int? = null,
        val errorMessage: String? = null
    ) : RemoteResult<Nothing>()

    object Empty : RemoteResult<Nothing>()
}