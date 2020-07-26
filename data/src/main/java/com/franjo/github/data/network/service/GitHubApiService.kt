package com.franjo.github.data.network.service

import com.franjo.github.data.network.dto.github_repository.GithubApiResponse
import com.franjo.github.domain.shared.SORT_STARS
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    // enum constants to match the query values our service expects
    enum class GithubApiFilter(val value: String) {
        SORT_STARS(value = "stars"),
        SORT_FORKS(value = "forks"),
        SORT_UPDATES(value = "updates")
    }

    // Get repos initially ordered by stars.
    @GET(SEARCH_REPOSITORY_PATH)
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sortBy: String = SORT_STARS,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubApiResponse

}