package com.franjo.github.data.dataSource.network.model.token

import com.squareup.moshi.Json

// Request a user's GitHub identity
data class AccessTokenRequest(
  @Json(name = "client_id") val clientId: String?,
  @Json(name = "client_secret") val clientSecret: String?,
  @Json(name = "code") val accessCode: String?,
  @Json(name = "refresh_token") val refreshToken: String?,
  @Json(name = "grant_type") val grantType: String?
)
