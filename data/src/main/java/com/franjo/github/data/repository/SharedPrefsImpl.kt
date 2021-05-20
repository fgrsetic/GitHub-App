package com.franjo.github.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.franjo.github.domain.repository.ISharedPrefs
import java.util.concurrent.ConcurrentHashMap

class SharedPrefsImpl(private val context: Context?) : ISharedPrefs {

  @Volatile
  private var cacheBoolean = ConcurrentHashMap<String, Boolean>()

  @Volatile
  private var cacheString = ConcurrentHashMap<String, String>()

  @Volatile
  private var cacheInt = ConcurrentHashMap<String, Int>()

  @Volatile
  private var cacheLong = ConcurrentHashMap<String, Long>()

  private fun getPreferences(): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
  }

  private fun getPreferencesEditor(): SharedPreferences.Editor {
    return getPreferences().edit()
  }

  override fun saveValue(key: String, value: Any) {
    return when (value) {
      is String -> saveString(key, value)
      is Boolean -> saveBoolean(key, value)
      is Int -> saveInt(key, value)
      is Long -> saveLong(key, value)
      else -> throw IllegalArgumentException("Value type not supported")
    }
  }

  override fun getValue(key: String, defaultValue: Any): Any? {
    return when (defaultValue) {
      is Boolean -> getBoolean(key, defaultValue)
      is String -> getString(key, defaultValue)
      is Int -> getInt(key, defaultValue)
      is Long -> getLong(key, defaultValue)
      else -> throw IllegalArgumentException("Value type not supported")
    }
  }

  private fun saveInt(key: String, value: Int) {
    cacheInt[key] = value
    getPreferencesEditor().putInt(key, value).apply()
  }

  private fun saveLong(key: String, value: Long) {
    cacheLong[key] = value
    getPreferencesEditor().putLong(key, value).apply()
  }

  private fun saveString(key: String, value: String) {
    cacheString[key] = value
    getPreferencesEditor().putString(key, value).apply()
  }

  private fun saveBoolean(key: String, value: Boolean) {
    cacheBoolean[key] = value
    getPreferencesEditor().putBoolean(key, value).apply()
  }

  private fun getString(key: String, defaultValue: String): String? {
    var result = cacheString[key]
    if (result == null) {
      result = getPreferences().getString(key, defaultValue)
      if (result != null) {
        cacheString[key] = result
      }
    }
    return result
  }

  private fun getInt(key: String, defaultValue: Int): Int {
    var result = cacheInt[key]
    if (result == null) {
      result = getPreferences().getInt(key, defaultValue)
      cacheInt[key] = result
    }
    return result
  }

  private fun getLong(key: String, defaultValue: Long): Long {
    var result = cacheLong[key]
    if (result == null) {
      result = getPreferences().getLong(key, defaultValue)
      cacheLong[key] = result
    }
    return result
  }

  private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
    var result = cacheBoolean[key]
    if (result == null) {
      result = getPreferences().getBoolean(key, defaultValue)
      cacheBoolean[key] = result
    }
    return result
  }
}
