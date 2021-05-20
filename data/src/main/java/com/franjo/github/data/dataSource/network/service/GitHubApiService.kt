package com.franjo.github.data.dataSource.network.service

import com.franjo.github.data.dataSource.network.dto.repository.RepositoryApiResponse
import com.franjo.github.data.dataSource.network.dto.token.AccessTokenResponse
import com.franjo.github.data.dataSource.network.dto.token.AuthorizationTokenRequest
import com.franjo.github.data.dataSource.network.dto.user.AuthenticatedUserResponse
import com.franjo.github.data.dataSource.network.dto.user.UserApiResponse
import com.franjo.github.domain.shared.SORT_STARS
import retrofit2.http.*

interface GitHubApiService {

  // https://api.github.com/search/repositories?q=te&sort=forks&page=1&per_page=30
  // Get repos initially ordered by stars.
  @GET("search/repositories")
  suspend fun searchRepositories(
    @Query("q") query: String,
    @Query("sort") sortBy: String = SORT_STARS,
    @Query("page") pageNumber: Int,
    @Query("per_page") itemsPerPage: Int
  ): RepositoryApiResponse

  // userName can have special characters,
  // so to avoid retrofit encoding, use property encoded
  @GET("users/{userName}")
  suspend fun getUserData(
    @Path("userName", encoded = true) userName: String
  ): UserApiResponse

  // Get access token to make request with token for authenticated user
  @POST
  suspend fun getAccessToken(
    @Url url: String,
    @Body authorizationTokenBody: AuthorizationTokenRequest
  ): AccessTokenResponse

  // Get authenticated user after sending token
  @GET(AUTHENTICATED_USER_PATH)
  suspend fun getAuthenticatedUserData(): AuthenticatedUserResponse
}
