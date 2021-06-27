package com.franjo.github.domain.usecase

import com.franjo.github.domain.model.authorization.AuthorizationUrl
import com.franjo.github.domain.repository.IAuthorizationRepository
import javax.inject.Inject

class GenerateAuthorizationUrl @Inject constructor(
  private val repository: IAuthorizationRepository
) {

  operator fun invoke(): AuthorizationUrl = repository.authorizationUrl()
}
