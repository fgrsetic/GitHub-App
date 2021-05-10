package com.franjo.github.data.util

import com.franjo.github.domain.shared.ResultWrapper
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    coroutineContext: CoroutineContext,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(coroutineContext) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            ResultWrapper.Error(throwable)
//            when (throwable) {
//                is IOException -> ResultWrapper.NetworkError
//                is HttpException -> {
//                    val code = throwable.code()
//                    val errorResponse = convertErrorBody(throwable)
//                    ResultWrapper.GenericError(code, errorResponse)
//                }
//                else -> ResultWrapper.GenericError(null, null)
        }
    }
}

// private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
//    return try {
//        throwable.response()?.errorBody()?.source()?.let {
//            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
//            moshiAdapter.fromJson(it)
//        }
//    } catch (exception: Exception) {
//        null
//    }
// }
