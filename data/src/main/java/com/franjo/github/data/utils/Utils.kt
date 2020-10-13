package com.franjo.github.data.utils

import com.franjo.github.domain.shared.ResultWrapper
import retrofit2.Response
import timber.log.Timber


suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): ResultWrapper<T?> {
    val result = try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to IOException
        Timber.tag("API call").e(e)
        return ResultWrapper.Error(e)
    }
    return if (result.isSuccessful && result.body() != null) {
        ResultWrapper.Success(result.body())
    } else {
        ResultWrapper.Error(Throwable())
    }
}

