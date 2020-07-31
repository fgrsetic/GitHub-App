package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IAuthenticationRepository
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.shared.UseCaseWithParams
import javax.inject.Inject

class GetAccessToken @Inject constructor(
    private val repository: IAuthenticationRepository
) : UseCaseWithParams<String, ResultWrapper<Exception, Unit>>() {

    // Fetch result if token is success
    override suspend fun buildUseCase(params: String): ResultWrapper<Exception, Unit> {
        return repository.getAccessToken(params)
    }
}