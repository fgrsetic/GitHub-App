package com.franjo.github.data.dataSource.network.service

const val BASE_URL = "https://api.github.com/"
const val SEARCH_REPOSITORY_PATH = "search/repositories"
const val USER_PATH = "/users/{userName}"

// Request a user's Github identity => login feature
const val AUTHORIZE_USER_URL = "https://github.com/login/oauth/authorize"

// Exchange this code for an access token => this uri is overridden and needs no token in header
const val AUTHORIZATION_TOKEN_URL = "https://github.com/login/oauth/access_token"

// Use the access token to access the API => needs token in header
const val AUTHENTICATED_USER_PATH = "/user" // look base url => "https://api.github.com/"
