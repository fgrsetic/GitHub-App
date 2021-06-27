package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IAuthorizationRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(
  private val repository: IAuthorizationRepository
) {
  operator fun invoke() = repository.logout()
}