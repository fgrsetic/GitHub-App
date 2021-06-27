package com.franjo.github.data.dataSource.network

import com.franjo.github.data.dataSource.network.model.user.UserApiResponse
import com.franjo.github.data.dataSource.network.model.user.asDomainObject
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.data.dataSource.network.utils.NetworkUtil
import com.franjo.github.domain.di.IODispatcher
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.shared.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
  private val service: GitHubApiService,
  private val networkUtil: NetworkUtil,
  @IODispatcher private val dispatcher: CoroutineDispatcher
) {
  suspend fun fetchUserData(userName: String): ResultWrapper<User> =
    networkUtil.safeApiCall((dispatcher), {
      service.getUserData(userName).asDomainObject()
    })
}
