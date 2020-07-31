package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IAuthenticationRepository
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.shared.UseCaseWithParams
import javax.inject.Inject

class GetAccessToken @Inject constructor(
    private val repository: IAuthenticationRepository
) : UseCaseWithParams<String, ResultWrapper<Exception, Unit>>() {

    override suspend fun buildUseCase(params: String): ResultWrapper<Exception, Unit> =
        repository.getAccessToken(params)

}