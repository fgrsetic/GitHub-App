package com.franjo.github.domain.repository

import com.franjo.github.domain.shared.ResultWrapper

interface IAuthenticationRepository {


    suspend fun getAccessToken(
        code: String
    ): ResultWrapper<Exception, Unit>
}