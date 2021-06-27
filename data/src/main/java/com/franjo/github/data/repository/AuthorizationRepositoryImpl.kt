package com.franjo.github.data.repository

import com.franjo.github.data.dataSource.network.model.token.asDomainObject
import com.franjo.github.data.dataSource.network.model.user.asDomainObject
import com.franjo.github.data.dataSource.network.service.Authorization
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.data.dataSource.network.utils.AuthorizationUtil
import com.franjo.github.data.dataSource.network.utils.NetworkUtil
import com.franjo.github.domain.di.IODispatcher
import com.franjo.github.domain.model.authorization.AccessToken
import com.franjo.github.domain.model.authorization.AuthorizationUrl
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.repository.IAuthorizationRepository
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import com.franjo.github.domain.shared.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
  private val apiService: GitHubApiService,
  @IODispatcher private val dispatcher: CoroutineDispatcher,
  private val networkUtil: NetworkUtil,
  private val authorizationUtil: AuthorizationUtil,
  private val encryptedPrefs: IEncryptedPrefs
) : IAuthorizationRepository {


  override fun isUserLoggedIn(): Boolean = encryptedPrefs.getValue(ACCESS_TOKEN_KEY, "") != null

  override fun authorizationUrl(): AuthorizationUrl = authorizationUtil.authorizationUrl()

  override suspend fun login(accessCode: String): ResultWrapper<AccessToken> {
    val result = networkUtil.safeApiCall((dispatcher), {
      apiService.getAccessToken(
        Authorization.AUTHORIZE_TOKEN_URL,
        authorizationUtil.accessTokenRequest(accessCode)
      ).asDomainObject()
    })
    // After sending authorization token (accessCode) we received access token
    // We saved it in secured prefs and we will send it with interceptor in header as parameter
    if (result is ResultWrapper.Success<AccessToken>) {
      encryptedPrefs.saveValue(ACCESS_TOKEN_KEY, result.data.accessToken)
    }
    return result
  }

  // With access token in header we can fetch private user data
  override suspend fun getAuthenticatedUser():
    AuthenticatedUser {
    return apiService.getAuthenticatedUserData().asDomainObject()
  }

  override fun logout() {
    encryptedPrefs.deleteAccessToken()
  }

}
