package com.franjo.github.domain.repository

interface ILoginRepository {

    fun login()

    suspend fun getAccessToken(code: String)
}