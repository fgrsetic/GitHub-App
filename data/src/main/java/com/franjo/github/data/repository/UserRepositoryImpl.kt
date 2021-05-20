package com.franjo.github.data.repository

import com.franjo.github.data.dataSource.network.UserRemoteDataSource
import com.franjo.github.data.dataSource.network.dto.user.asDomainObject
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.repository.IUserRepository
import com.franjo.github.domain.shared.ResultWrapper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
  private val dataSource: UserRemoteDataSource
) : IUserRepository {

  override suspend fun getUserData(author: String): ResultWrapper<User> {
    return when (val result = dataSource.fetchUserData(author)) {
      is ResultWrapper.Success -> ResultWrapper.Success(result.data.asDomainObject())
      else -> ResultWrapper.Error(Throwable())
    }
  }
}
