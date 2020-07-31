package com.franjo.github.domain.repository

import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.shared.ResultWrapper

interface IAuthenticationRepository {

    suspend fun getAccessToken(
        code: String
    ): ResultWrapper<Exception, Unit>

    suspend fun getAuthenticatedUser(): AuthenticatedUser
}