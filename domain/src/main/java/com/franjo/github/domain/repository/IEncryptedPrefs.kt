package com.franjo.github.domain.repository

interface IEncryptedPrefs {
  fun saveValue(key: String, value: String)
  fun getValue(key: String, defaultValue: String): String?
  fun deleteAccessToken()
}
