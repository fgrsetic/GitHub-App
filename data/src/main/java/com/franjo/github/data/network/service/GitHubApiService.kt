package com.franjo.github.data.network.service

import com.franjo.github.data.network.dto.github_repository.GithubApiResponse
import com.franjo.github.domain.shared.SORT_STARS
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    // Get repos initially ordered by stars.
    @GET(SEARCH_REPOSITORY_PATH)
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sortBy: String = SORT_STARS,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubApiResponse

}