package com.franjo.github.data.dataSource.network.utils

import com.franjo.github.data.BuildConfig
import com.franjo.github.domain.model.authorization.AuthorizationUrl
import com.franjo.github.data.dataSource.network.model.token.AccessTokenRequest
import com.franjo.github.data.dataSource.network.service.Authorization
import com.franjo.github.domain.shared.REDIRECT_URI_CALLBACK

class AuthorizationUtil {

  fun accessTokenRequest(accessCode: String) = AccessTokenRequest(
    BuildConfig.CLIENT_ID,
    BuildConfig.CLIENT_SECRET,
    accessCode = accessCode,
    refreshToken = null,
    grantType = null
  )

  fun authorizationUrl() = AuthorizationUrl(
    url = Authorization.AUTHORIZE_USER_URL,
    clientId = BuildConfig.CLIENT_ID,
    redirectUrl = REDIRECT_URI_CALLBACK
  )

}