package com.franjo.github.data.network.service

import com.franjo.github.data.network.dto.github_repository.RepositoryApiResponse
import com.franjo.github.data.network.dto.github_user.UserApiResponse
import com.franjo.github.domain.shared.SORT_STARS
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    // https://api.github.com/search/repositories?q=te&sort=forks&page=1&per_page=30

    // Get repos initially ordered by stars.
    @GET(SEARCH_REPOSITORY_PATH)
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sortBy: String = SORT_STARS,
        @Query("page") pageNumber: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepositoryApiResponse

    @GET(USER_PATH)
    fun getUserDataAsync(
        @Path("userName") userName: String
    ): Deferred<UserApiResponse>
}