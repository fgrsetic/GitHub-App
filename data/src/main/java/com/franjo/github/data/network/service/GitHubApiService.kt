package com.franjo.github.data.network.service

import com.franjo.github.data.network.dto.githubRepository.RepositoryApiResponse
import com.franjo.github.data.network.dto.githubUser.AuthenticatedUserResponse
import com.franjo.github.data.network.dto.githubUser.UserApiResponse
import com.franjo.github.data.network.dto.token.AccessTokenResponse
import com.franjo.github.data.network.dto.token.AuthorizationTokenRequest
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.shared.SORT_STARS
import retrofit2.Response
import retrofit2.http.*

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
    suspend fun getUserData(
        @Path("userName") userName: String
    ): Response<UserApiResponse>

    // Get access token to make request with token for authenticated user
    @POST
    suspend fun getAccessToken(
        @Url url: String,
        @Body authorizationTokenBody: AuthorizationTokenRequest): Response<AccessTokenResponse>

    // Get authenticated user after sending token
    @GET(AUTHENTICATED_USER_PATH)
    suspend fun getAuthenticatedUserData(): AuthenticatedUserResponse

}

