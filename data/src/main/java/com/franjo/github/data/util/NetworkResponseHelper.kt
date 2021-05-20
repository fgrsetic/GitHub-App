package com.franjo.github.data.util

import com.franjo.github.domain.shared.ResultWrapper
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> safeApiCall(
  coroutineContext: CoroutineContext,
  apiCall: suspend () -> T
): ResultWrapper<T> {
  return withContext(coroutineContext) {
    try {
      ResultWrapper.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
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
}
