package com.franjo.github.data.repository

import com.franjo.github.data.BuildConfig
import com.franjo.github.data.network.dto.github_user.asDomainObject
import com.franjo.github.data.network.dto.token.AuthorizationTokenRequest
import com.franjo.github.data.network.service.GitHubPublicUserApiService
import com.franjo.github.data.network.service.GitHubPrivateUserApiService
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.repository.IAuthenticationRepository
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiServicePublicUser: GitHubPublicUserApiService,
    private val privateUserApiService: GitHubPrivateUserApiService,
    private val encryptedPrefs: IEncryptedPrefs
) : IAuthenticationRepository {

    // Save token in prefs
    override suspend fun getAccessToken(code: String) =
        try {
            val result = privateUserApiService.getAccessToken(
                AuthorizationTokenRequest(
                    BuildConfig.CLIENT_ID,
                    BuildConfig.CLIENT_SECRET,
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
    override suspend fun getAuthenticatedUser(accessToken: String)
            : AuthenticatedUser {
        return apiServicePublicUser.getAuthenticatedUserData("token $accessToken").asDomainObject()
    }

}