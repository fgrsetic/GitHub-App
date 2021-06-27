package com.franjo.github.data.dataSource.network.model.token

import com.franjo.github.domain.model.authorization.AccessToken
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AccessTokenResponse(
  @Json(name = "access_token") val accessToken: String?,
  @Json(name = "token_type") val tokenType: String?
)

fun AccessTokenResponse.asDomainObject() = AccessToken(
  accessToken = this.accessToken.orEmpty(),
  tokenType = this.tokenType.orEmpty()
)


