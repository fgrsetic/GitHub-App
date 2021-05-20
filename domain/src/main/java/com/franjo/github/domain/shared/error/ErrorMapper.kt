package com.franjo.github.domain.shared.error

import java.net.HttpURLConnection

/**
 * Convert outer errors into app acceptable entities.
 * Example: use implementation to convert
 * to valid [ErrorEntity] when network layer throws
 * [HttpURLConnection.HTTP_NOT_FOUND] exception
 */
interface ErrorMapper {

  fun map(throwable: Throwable, errorType: Class<*>?): ErrorEntity
}
