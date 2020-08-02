package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.ILoginRepository
import javax.inject.Inject

class GetLogin @Inject constructor(
    private val loginRepository: ILoginRepository
) {

    fun login() = loginRepository.login()

}