package com.franjo.github.data.repository

import com.franjo.github.data.BuildConfig.CLIENT_ID
import com.franjo.github.data.BuildConfig.CLIENT_SECRET
import com.franjo.github.data.network.dto.githubUser.asDomainObject
import com.franjo.github.data.network.dto.token.AuthorizationTokenRequest
import com.franjo.github.data.network.service.AUTHORIZATION_TOKEN_URL
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.repository.IAuthenticationRepository
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val encryptedPrefs: IEncryptedPrefs
) : IAuthenticationRepository {

    // Save token in prefs
    override suspend fun getAccessToken(code: String) =
        try {
            val result = apiService.getAccessToken(
                AUTHORIZATION_TOKEN_URL,
                AuthorizationTokenRequest(
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code
                )
            )
            // After sending authorization token (code) we received access token
            // We saved it in secured prefs and we will send it with interceptor in header as parameter
            encryptedPrefs.saveValue(ACCESS_TOKEN_KEY, result.body()!!.accessToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    // With access token in header we can fetch private user data
    override suspend fun getAuthenticatedUser()
            : AuthenticatedUser {
        return apiService.getAuthenticatedUserData().asDomainObject()
    }

}