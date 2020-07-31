package com.franjo.github.data.network.service

const val BASE_URL = "https://api.github.com/"

const val SEARCH_REPOSITORY_PATH = "search/repositories"

const val USER_PATH = "/users/{userName}"

// Authentication
// 1. Developer registers at the GitHub service for the new App	-> Receives clientID, client secret
// 2. Registered App requests authorization to access service data
// 3. The app opens a special page of the service, where User logs in an then permits the app access
// 4. If successful, the service will respond with an authorization token
// 5. App uses the authorization token to request an access token

const val AUTHORIZE_USER_URL =  "https://github.com/login/oauth/authorize"
const val AUTHORIZATION_TOKEN_URL = "https://github.com/login/oauth/access_token"

// 6. Save token
const val ACCESS_TOKEN_KEY = "access_token"
const val SECRET_SHARED_PREFS = "secret_shared_prefs"

// 7. Access token can be used for all following requests
const val AUTHENTICATED_USER_PATH = "/user"




