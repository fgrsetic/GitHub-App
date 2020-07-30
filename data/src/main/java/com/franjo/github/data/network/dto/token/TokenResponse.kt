package com.franjo.github.data.network.dto.token
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "access_token")
    val accessToken: String = "",
    @Json(name = "scope")
    val scope: String = "",
    @Json(name = "token_type")
    val tokenType: String = ""
)