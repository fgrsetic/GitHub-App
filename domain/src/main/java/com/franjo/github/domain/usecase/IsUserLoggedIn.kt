package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IAuthorizationRepository
import javax.inject.Inject

class IsUserLoggedIn @Inject constructor(
  private val repository: IAuthorizationRepository
  ) {
  operator fun invoke(): Boolean = repository.isUserLoggedIn()
}