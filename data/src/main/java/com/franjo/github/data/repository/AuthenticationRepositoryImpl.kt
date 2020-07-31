package com.franjo.github.data.repository

import com.franjo.github.data.network.dto.token.TokenRequest
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.domain.repository.IAuthenticationRepository
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val apiService: GitHubApiService,
    private val encryptedPrefs: IEncryptedPrefs
) : IAuthenticationRepository {

    override suspend fun getAccessToken(code: String): ResultWrapper<Exception, Unit> =
        withContext(dispatcherProvider.provideIOContext()) {
            when (val result = try {
                apiService.getAccessToken(
                    TOKEN_URL,
                    TokenRequest(CLIENT_ID, CLIENT_SECRET, code)
                )
            } catch (e: Exception) {
                return@withContext if (e is HttpException) {
                    ResultWrapper.build { throw Exception(e.message()) }
                } else {
                    ResultWrapper.build { throw Exception(e.message) }
                }
            }) {
                is ResultWrapper.Success -> {
                    encryptedPrefs.saveValue(ACCESS_TOKEN_KEY, result.value.accessToken)
                    ResultWrapper.build { Unit }
                }
                is ResultWrapper.Error -> result
            }
        }
}