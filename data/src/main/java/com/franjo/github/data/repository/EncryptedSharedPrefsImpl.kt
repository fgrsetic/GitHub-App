package com.franjo.github.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import com.franjo.github.domain.shared.SECRET_SHARED_PREFS

class EncryptedSharedPrefsImpl(context: Context) : IEncryptedPrefs {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        SECRET_SHARED_PREFS,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveValue(key: String, value: String) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, value)
            .apply()
    }

    override fun getValue(key: String, defaultValue: String): String? =
        sharedPreferences.getString(key, defaultValue)
}