package com.franjo.github.data.dataSource.network.mapper

import com.franjo.github.domain.shared.ErrorModel

// map response error to appropriate model from github documentation
interface ErrorMapper {
  fun map(throwable: Throwable, errorType: Class<*>?): ErrorModel
}
