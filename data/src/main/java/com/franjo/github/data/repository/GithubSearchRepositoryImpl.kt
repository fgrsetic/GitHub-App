package com.franjo.github.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.IGithubSearchRepository
import com.franjo.github.domain.shared.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubSearchRepositoryImpl @Inject constructor(
  private val apiService: GitHubApiService
) : IGithubSearchRepository<Flow<PagingData<Repo>>> {

  // Search repositories where names match the query
  // The Flow emits a new PagingData whenever new data is loaded by the PagingSource
  // The Flow is coroutines library for representing an async sequence, or stream, of values
  override fun getSearchResultStream(query: String, sortBy: String): Flow<PagingData<Repo>> {
    // The Pager.flow creates a Flow<PagingData> based on a configuration
    // and a function that defines how to instantiate the PagingSource
    return Pager(
      config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
      pagingSourceFactory = {
        GithubSearchPagingSource(apiService, query, sortBy)
      }
    ).flow
  }
}
