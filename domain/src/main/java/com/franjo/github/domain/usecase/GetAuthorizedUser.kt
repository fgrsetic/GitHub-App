package com.franjo.github.domain.usecase

import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.repository.IAuthorizationRepository
import javax.inject.Inject

class GetAuthenticatedUser @Inject constructor(
  private val repository: IAuthorizationRepository
) {

  // Fetch authorized user result if access token is success
  suspend fun execute(): AuthenticatedUser =
    repository.getAuthenticatedUser()
}
