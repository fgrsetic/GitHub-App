package com.franjo.github.data.network.service

import com.franjo.github.data.network.dto.github_repository.NetworkRepositoryContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    @GET(SEARCH_REPOSITORY_PATH)
    suspend fun getRepositorySearchList(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Response<NetworkRepositoryContainer>

}