package com.franjo.github.domain.shared

/**
 * ResultWrapper Wrapper <Left = Exception, Right = Value/Success>
 */
sealed class ResultWrapper<out E, out V> {

    data class Success<out V>(val value: V) : ResultWrapper<Nothing, V>()
    data class Error<out E>(val error: E) : ResultWrapper<E, Nothing>()

    companion object Factory {
        // Higher order functions take functions as parameters or return a function
        // Kotlin has function types name: () -> V
        inline fun <V> build(function: () -> V): ResultWrapper<Exception, V> =
            try {
                Success(function.invoke())
            } catch (e: java.lang.Exception) {
                Error(e)
            }
    }

}