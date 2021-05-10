package com.franjo.github.domain.repository

import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.shared.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    suspend fun getUserData(userName: String): ResultWrapper<User>
}