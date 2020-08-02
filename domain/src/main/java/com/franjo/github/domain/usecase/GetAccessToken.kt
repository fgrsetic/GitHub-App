package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IAuthenticationRepository
import javax.inject.Inject

class GetAccessToken @Inject constructor(
    private val repository: IAuthenticationRepository
)  {

    suspend fun execute (params: String) =
        repository.getAccessToken(code = params)

}