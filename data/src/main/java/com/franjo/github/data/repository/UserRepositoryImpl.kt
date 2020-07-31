package com.franjo.github.data.repository

import com.franjo.github.data.network.dto.github_user.asDomainObject
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.repository.IUserRepository
import com.franjo.github.domain.shared.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: GitHubApiService
) : IUserRepository {

    // User data
    override suspend fun getUserData(query: String): User {
        return withContext(dispatcherProvider.provideIOContext()) {
            apiService.getUserDataAsync(userName = query).asDomainObject()
        }
    }
}