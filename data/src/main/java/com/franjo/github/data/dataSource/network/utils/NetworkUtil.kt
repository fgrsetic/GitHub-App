package com.franjo.github.data.dataSource.network.utils

import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.data.dataSource.network.mapper.ErrorMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NetworkUtil @Inject constructor(private val errorMapper: ErrorMapper) {

  suspend fun <T> safeApiCall(
    coroutineContext: CoroutineContext,
    apiCall: suspend () -> T,
    errorType: Class<*>? = null
  ): ResultWrapper<T> {
    return withContext(coroutineContext) {
      try {
        ResultWrapper.Success(apiCall())
      } catch (throwable: Throwable) {
        ResultWrapper.Error(errorMapper.map(throwable, errorType))
      }
    }
  }
}
