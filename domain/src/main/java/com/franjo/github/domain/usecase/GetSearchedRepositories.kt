package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IGithubSearchRepository
import javax.inject.Inject

class GetSearchedRepositories<T> @Inject constructor(
  private val githubSearchRepository: IGithubSearchRepository<T>
) {

  fun getSearchResultStream(query: String, sortBy: String): T =
    githubSearchRepository.getSearchResultStream(
      query,
      sortBy
    )
}
