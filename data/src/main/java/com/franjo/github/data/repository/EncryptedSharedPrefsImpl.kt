package com.franjo.github.data.repository

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import com.franjo.github.domain.shared.SECRET_SHARED_PREFS

class EncryptedSharedPrefsImpl(context: Context) : IEncryptedPrefs {

  // This is equivalent to using deprecated MasterKeys.AES256_GCM_SPEC
  private var spec = KeyGenParameterSpec.Builder(
    MasterKey.DEFAULT_MASTER_KEY_ALIAS,
    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
  )
    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
    .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
    .build()

  private val masterKeyAlias = MasterKey.Builder(context)
    .setKeyGenParameterSpec(spec)
    .build()

  private val sharedPreferences = EncryptedSharedPreferences.create(
    context,
    SECRET_SHARED_PREFS,
    masterKeyAlias,
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
