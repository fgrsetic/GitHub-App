package com.franjo.github.domain.shared

sealed class ResultWrapper<out T> {
  data class Success<out T>(val data: T) : ResultWrapper<T>()
  data class Error(val error: ErrorModel) : ResultWrapper<Nothing>()
}
