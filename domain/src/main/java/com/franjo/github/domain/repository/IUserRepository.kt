package com.franjo.github.domain.repository

import com.franjo.github.domain.model.user.User

interface IUserRepository {

    suspend fun getUserData(query: String): User
}