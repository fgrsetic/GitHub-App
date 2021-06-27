package com.franjo.github.data.dataSource.network.service

object Api {
  const val BASE_URL = "https://api.github.com/"
}

object Authorization {
  private const val AUTHORIZE_BASE_URL = "https://github.com/login/oauth/"
  // Request a user's Github identity
  const val AUTHORIZE_USER_URL = "${AUTHORIZE_BASE_URL}authorize"
  // Exchange this code for an access token => this uri is overridden and needs no token in header
  const val AUTHORIZE_TOKEN_URL = "${AUTHORIZE_BASE_URL}access_token"
}




