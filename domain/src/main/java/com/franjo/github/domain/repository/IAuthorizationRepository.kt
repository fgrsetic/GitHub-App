package com.franjo.github.domain.repository

import com.franjo.github.domain.model.authorization.AccessToken
import com.franjo.github.domain.model.authorization.AuthorizationUrl
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.shared.ResultWrapper

interface IAuthorizationRepository {
  fun isUserLoggedIn(): Boolean
  fun authorizationUrl(): AuthorizationUrl
  suspend fun login(accessCode: String): ResultWrapper<AccessToken>
  fun logout()
  suspend fun getAuthenticatedUser(): AuthenticatedUser
}
