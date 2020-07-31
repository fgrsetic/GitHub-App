package com.franjo.github.data.network.dto.token

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Request a user's GitHub identity
@JsonClass(generateAdapter = true)
data class AuthorizationTokenRequest(
    @Json(name = "client_id")
    val clientId: String,
    @Json(name = "client_secret")
    val clientSecret: String,
    val code: String
)