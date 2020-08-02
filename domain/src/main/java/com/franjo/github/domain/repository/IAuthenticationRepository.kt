package com.franjo.github.domain.repository

import com.franjo.github.domain.model.user.AuthenticatedUser

interface IAuthenticationRepository {

    suspend fun getAccessToken(code: String)

    suspend fun getAuthenticatedUser(token: String): AuthenticatedUser
}