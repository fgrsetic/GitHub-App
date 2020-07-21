package com.franjo.github.data.network

import okhttp3.Response
import retrofit2.http.GET

interface RestApiInterface {

    @GET(REPOSITORY_PATH)
    fun getRepositoryList(): Response<>

    @GET(REPOSITORY_DETAILS_PATH)
    fun getRepositoryDetails(): Response<>
}