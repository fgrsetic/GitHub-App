package com.franjo.github.data.repository

import com.franjo.github.data.network.dto.github_user.asDomainObject
import com.franjo.github.data.network.dto.token.AuthorizationTokenRequest
import com.franjo.github.data.network.service.ACCESS_TOKEN_KEY
import com.franjo.github.data.network.service.AUTHORIZATION_TOKEN_URL
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.repository.IAuthenticationRepository
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.CLIENT_ID
import com.franjo.github.domain.shared.CLIENT_SECRET
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.ResultWrapper
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: GitHubApiService,
    private val encryptedPrefs: IEncryptedPrefs
) : IAuthenticationRepository {

    override suspend fun getAccessToken(code: String): ResultWrapper<Exception, Unit> =
        withContext(dispatcherProvider.provideIOContext()) {
            val result = try {
                apiService.getAccessToken(
                    AUTHORIZATION_TOKEN_URL,
                    AuthorizationTokenRequest(CLIENT_ID, CLIENT_SECRET, code)
                )
            } catch (e: Exception) {
                return@withContext if (e is HttpException) {
                    ResultWrapper.build { throw Exception(e.message()) }
                } else {
                    ResultWrapper.build { throw Exception(e.message) }
                }
            }

            return@withContext if (result.isSuccessful && result.body() != null) {
                return@withContext ResultWrapper.build {
                    // After sending authorization token (code) we received access token
                    // We saved it in secured prefs and we will send it with interceptor in header as parameter
                    encryptedPrefs.saveValue(ACCESS_TOKEN_KEY, result.body()!!.accessToken)
                }
            } else {
                ResultWrapper.build {
                    throw Exception(
                        result.message() ?: "Unknown error occurred"
                    )
                }
            }
        }


    // With access token in header we can fetch private user data
    override suspend fun getAuthenticatedUser(): AuthenticatedUser =
        withContext(dispatcherProvider.provideIOContext()) {
            apiService.getPrivateUserData().asDomainObject()
        }

}