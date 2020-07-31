package com.franjo.github.domain.usecase

import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.repository.IUserRepository
import javax.inject.Inject

class GetUserData @Inject constructor(
    private val userData: IUserRepository
) {

    suspend fun execute(query: String): User =
        userData.getUserData(query)

}