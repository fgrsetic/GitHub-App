package com.franjo.github.data.dataSource.network

import com.franjo.github.data.dataSource.network.dto.githubUser.UserApiResponse
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.data.util.safeApiCall
import com.franjo.github.domain.di.IODispatcher
import com.franjo.github.domain.shared.ResultWrapper
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class UserRemoteDataSource @Inject constructor(
    private val service: GitHubApiService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchUserData(userName: String): ResultWrapper<UserApiResponse> =
        safeApiCall(dispatcher) { service.getUserData(userName) }
}
