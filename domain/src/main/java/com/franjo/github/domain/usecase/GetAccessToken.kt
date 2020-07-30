package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.ILoginRepository
import javax.inject.Inject

class GetAccessToken @Inject constructor(
    private val loginRepository: ILoginRepository
) {
    suspend fun execute(code: String) = loginRepository.getAccessToken(code)
}