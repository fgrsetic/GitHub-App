package com.franjo.github.domain.shared

sealed class ResultWrapper<out T> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val error: Throwable) : ResultWrapper<Nothing>()

    /**
     * If this [ResultWrapper] is of type [ResultWrapper.Success], returns the available data
     * from it. Otherwise, returns "null"
     */
    fun get(): T? {
        return when (this) {
            is Success -> data
            is Error -> null
        }
    }

    /**
     * If this [ResultWrapper] is of type [ResultWrapper.Success], returns the available data
     * from it. Otherwise, returns "error"
     */
    fun getOrThrow(): T? {
        return when (this) {
            is Success -> data
            is Error -> throw error
        }
    }

    /**
     * If this [ResultWrapper] is of type [ResultWrapper.Error], returns the available error
     * from it. Otherwise, returns "null"
     */
    fun errorOrNull(): Throwable? {
        return (this as? Error)?.error
    }

    /**
     * If this [ResultWrapper] is of type [ResultWrapper.Error], returns the exception
     * Otherwise, does nothing
     */
    fun throwIfError() {
        if (this is Error) {
            throw error
        }
    }

    /**
     * Maps [ResultWrapper] of type T to result of type R by applying
     * [mapper] function
     */
    fun <R> map(mapper: (T) -> R): ResultWrapper<R> {
        return when (this) {
            is Success -> Success(mapper(data))
            is Error -> Error(error)
        }
    }

}