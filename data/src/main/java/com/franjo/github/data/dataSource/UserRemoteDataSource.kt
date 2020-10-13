package com.franjo.github.data.dataSource

import com.franjo.github.data.network.dto.githubUser.UserApiResponse
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.utils.safeApiCall
import com.franjo.github.domain.shared.ResultWrapper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val service: GitHubApiService
) {
    suspend fun fetchUserData(userName: String): ResultWrapper<UserApiResponse?> {
        return safeApiCall { service.getUserData(userName) }
    }
}