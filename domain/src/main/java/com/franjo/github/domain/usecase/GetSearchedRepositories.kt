package com.franjo.github.domain.usecase

import com.franjo.github.domain.repository.IGithubRepository
import javax.inject.Inject

class GetSearchedRepositories<T> @Inject constructor(
    private val githubRepository: IGithubRepository<T>
) {

    fun invoke(query: String, sortBy: String): T =
        githubRepository.getSearchResultStream(
            query,
            sortBy
        )

}