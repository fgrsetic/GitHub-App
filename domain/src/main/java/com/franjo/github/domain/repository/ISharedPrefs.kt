package com.franjo.github.domain.repository

interface ISharedPrefs {

    fun saveValue(key: String, value: Any)
    fun getValue(key: String, defaultValue: Any): Any?

}