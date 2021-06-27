package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IAuthorizationRepository
import javax.inject.Inject

class LoginUser @Inject constructor(
  private val repository: IAuthorizationRepository
) {
  suspend operator fun invoke(accessCode: String) =
    repository.login(accessCode)
}
