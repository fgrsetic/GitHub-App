package com.franjo.github.data.dataSource.network.service

import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor @Inject constructor(private val iEncryptedPrefs: IEncryptedPrefs) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = iEncryptedPrefs.getValue(ACCESS_TOKEN_KEY, "")
        val originalRequest = chain.request()
        val request: Request.Builder = originalRequest.newBuilder()
        if (!token.isNullOrEmpty()) {
            request.addHeader("Authorization", "token $token")
        }
        request.addHeader("Accept", "application/json")

        return chain.proceed(request.build())
    }
}
