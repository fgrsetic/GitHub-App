package com.franjo.github.domain.usecase

import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.repository.IUserRepository
import com.franjo.github.domain.shared.ResultWrapper
import javax.inject.Inject

class GetUserData @Inject constructor(
    private val userData: IUserRepository
) {

    suspend operator fun invoke(query: String): ResultWrapper<User> =
        userData.getUserData(query)
}