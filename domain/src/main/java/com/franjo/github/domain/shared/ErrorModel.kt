package com.franjo.github.domain.shared

// https://gist.github.com/subfuzion/669dfae1d1a27de83e69
sealed class ErrorModel {
  object NetworkError : ErrorModel()
  object NotFound : ErrorModel() // 404
  object Forbidden : ErrorModel() // 403
  object Unavailable : ErrorModel() // 503
  object Unknown : ErrorModel()
  data class GenericError<T>(val status: Int, val errorModel: T) : ErrorModel()
}
