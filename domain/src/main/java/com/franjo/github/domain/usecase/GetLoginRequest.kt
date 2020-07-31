package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.ILoginRepository
import com.franjo.github.domain.shared.UseCase
import javax.inject.Inject

class GetLoginRequest @Inject constructor(
    private val loginRepository: ILoginRepository
) : UseCase<Unit>() {

    override suspend fun buildUseCase() = loginRepository.login()

}