package com.franjo.github.domain.shared

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val throwable: Throwable) : ResultWrapper<Nothing>()
    // data class GenericError(val code: Int? = null, val error: ErrorResponse) : ResultWrapper<Nothing>()
}