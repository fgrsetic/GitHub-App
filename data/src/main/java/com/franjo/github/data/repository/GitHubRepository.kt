package com.franjo.github.data.repository

import com.franjo.github.data.network.dto.github_repository.NetworkRepositoryContainer
import com.franjo.github.data.network.dto.github_repository.asDomainObject
import com.franjo.github.data.network.service.RestApiInterface
import com.franjo.github.domain.model.Repository
import com.franjo.github.domain.repository.IGithubRepository
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.Result
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GitHubRepository(
    private val dispatcherProvider: DispatcherProvider,
    private val restApiInterface: RestApiInterface
) : IGithubRepository {


    override suspend fun loadRepositoriesFromServer(
        query: String,
        sort: String,
        page: Int
    ): Result<Exception, List<Repository>> =
        withContext(dispatcherProvider.provideIOContext()) {
            val result = try {
                restApiInterface.getRepositorySearchList(query, sort, page)
            } catch (e: Exception) {
                return@withContext if (e is HttpException) {
                    // Http exception
                    Result.build { throw Exception(e.message()) }
                } else {
                    // Throwable
                    Result.build { throw Exception(e.message) }
                }
            }

            return@withContext if (result.isSuccessful && result.body() != null) {
                Result.build { (result.body() as NetworkRepositoryContainer).asDomainObject() }
            } else {
                Result.build { throw Exception(result.message()) }
            }
        }

}