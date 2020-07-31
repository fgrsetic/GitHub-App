package com.franjo.github.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import com.franjo.github.data.network.service.ACCESS_TOKEN_KEY
import com.franjo.github.data.network.service.SECRET_SHARED_PREFS
import com.franjo.github.domain.repository.IEncryptedPrefs


class EncryptedSharedPrefsImpl(context: Context) : IEncryptedPrefs {

    @RequiresApi(Build.VERSION_CODES.M)
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyGenParameterSpec(AES256_GCM_SPEC)
        .build()

    @RequiresApi(Build.VERSION_CODES.M)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        SECRET_SHARED_PREFS,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun saveValue(key: String, value: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, value)
            .apply()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getValue(key: String, defaultValue: String): String? =
        sharedPreferences.getString(key, defaultValue)
}