package com.franjo.github.domain.model.authorization

data class AuthorizationUrl(
  val url: String,
  val clientId: String,
  val redirectUrl: String,
)
