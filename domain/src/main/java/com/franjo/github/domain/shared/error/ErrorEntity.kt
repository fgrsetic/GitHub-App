package com.franjo.github.domain.shared.error

sealed class ErrorEntity {

  object Network : ErrorEntity()
  object NotFound : ErrorEntity()
  object AccessDenied : ErrorEntity()
  object ServiceUnavailable : ErrorEntity()
  object Unknown : ErrorEntity()
  data class Custom<T>(val status: Int, val errorModel: T) : ErrorEntity()
}
