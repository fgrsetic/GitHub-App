package com.franjo.github.domain.repository

import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.shared.ResultWrapper

interface IUserRepository {

    suspend fun getUserData(query: String): ResultWrapper<User?>
}