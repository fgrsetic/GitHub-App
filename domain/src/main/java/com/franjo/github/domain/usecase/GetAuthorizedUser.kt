package com.franjo.github.domain.usecase

import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.repository.IAuthenticationRepository
import javax.inject.Inject

class GetAuthenticatedUser @Inject constructor(
    private val repository: IAuthenticationRepository
) {

    // Fetch authorized user result if access token is success
    suspend fun execute(token: String): AuthenticatedUser =
        repository.getAuthenticatedUser(token)
}