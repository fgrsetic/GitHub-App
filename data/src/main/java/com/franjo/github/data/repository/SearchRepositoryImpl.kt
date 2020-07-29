package com.franjo.github.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.franjo.github.data.data_source.SearchRepositoryPagingSource
import com.franjo.github.data.network.dto.github_user.UserApiResponse
import com.franjo.github.data.network.dto.github_user.asDomainObject
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.repository.IGithubRepository
import com.franjo.github.domain.repository.IUserRepository
import com.franjo.github.domain.shared.PAGE_SIZE
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService
) : IGithubRepository<Flow<PagingData<Repo>>>, IUserRepository {

    // Search repositories where names match the query
    // The Flow emits a new PagingData whenever new data is loaded by the PagingSource
    // The Flow is coroutines library for representing an async sequence, or stream, of values
    override fun getSearchResultStream(query: String, sortBy: String): Flow<PagingData<Repo>> {
        // The Pager.flow creates a Flow<PagingData> based on a configuration
        // and a function that defines how to instantiate the PagingSource
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchRepositoryPagingSource(
                    apiService,
                    query,
                    sortBy
                )
            }
        ).flow
    }

    override suspend fun getUserDataDeferredAsync(query: String): User = apiService.getUserData(userName = query).await().asDomainObject()

}