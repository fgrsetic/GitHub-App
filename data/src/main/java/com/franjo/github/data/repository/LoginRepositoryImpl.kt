package com.franjo.github.data.repository

import android.app.Application
import android.content.Intent
import android.net.Uri
import com.franjo.github.domain.repository.ILoginRepository
import com.franjo.github.domain.shared.AUTHORIZE_USER
import com.franjo.github.domain.shared.CLIENT_ID
import com.franjo.github.domain.shared.REDIRECT_URI_CALLBACK
import com.franjo.github.domain.shared.SCOPE
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val app: Application) : ILoginRepository {


    override fun login() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                AUTHORIZE_USER
                        + "?client_id=" + CLIENT_ID
                        + "&scope=" + SCOPE
                        + "&redirect_uri=" + REDIRECT_URI_CALLBACK
            )
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        app.startActivity(intent)
    }

    override suspend fun getAccessToken(code: String) {
        
    }
}