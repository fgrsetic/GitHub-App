package com.franjo.github.domain.model.authorization

data class AccessToken(
  val accessToken: String,
  val tokenType: String
)