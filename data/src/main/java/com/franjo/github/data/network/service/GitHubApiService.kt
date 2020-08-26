package com.franjo.github.data.network.service

import com.franjo.github.data.network.dto.github_repository.RepositoryApiResponse
import com.franjo.github.data.network.dto.github_user.AuthenticatedUserResponse
import com.franjo.github.data.network.dto.github_user.UserApiResponse
import com.franjo.github.data.network.dto.token.AccessTokenResponse
import com.franjo.github.data.network.dto.token.AuthorizationTokenRequest
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
    suspend fun getUserDataAsync(
        @Path("userName") userName: String
    ): UserApiResponse

    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @GET(AUTHENTICATED_USER_PATH)
    suspend fun getAuthenticatedUserData(@Header("Authorization") accessToken: String): AuthenticatedUserResponse

}

interface GitHubApiService2 {
    @POST(AUTHORIZATION_TOKEN_PATH)
    suspend fun getAccessToken(@Body authorizationTokenBody: AuthorizationTokenRequest): Response<AccessTokenResponse>
}