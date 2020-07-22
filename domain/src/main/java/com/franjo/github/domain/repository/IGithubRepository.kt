package com.franjo.github.domain.repository

import com.franjo.github.domain.entity.GithubRepositoryItem
import com.franjo.github.domain.shared.Result

interface IGithubRepository {

    suspend fun loadRepositoriesFromServer(
        query: String,
        sort: String,
        page: Int
    ): Result<Exception, List<GithubRepositoryItem>>
}