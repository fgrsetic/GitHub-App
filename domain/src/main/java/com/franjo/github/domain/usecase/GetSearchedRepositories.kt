package com.franjo.github.domain.usecase

import com.franjo.github.domain.model.Repository
import com.franjo.github.domain.model.SearchRequest
import com.franjo.github.domain.repository.IGithubRepository
import com.franjo.github.domain.shared.Result
import com.franjo.github.domain.shared.UseCaseWithParams
import javax.inject.Inject

class GetSearchedRepositories @Inject constructor(
    private val githubRepository: IGithubRepository
) : UseCaseWithParams<SearchRequest, Result<Exception, List<Repository>>>() {

    override suspend fun buildUseCase(params: SearchRequest): Result<Exception, List<Repository>> =
        githubRepository.loadRepositoriesFromServer(
            query = params.query,
            sort = params.sort,
            page = params.page
        )

}