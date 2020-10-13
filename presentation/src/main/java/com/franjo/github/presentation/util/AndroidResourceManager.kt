package com.franjo.github.presentation.util

import android.app.Application
import javax.inject.Inject

class AndroidResourceManager @Inject constructor(
    val application: Application
) : IResourceManager {


    override fun getLocalizedString(stringKey: Int): String {
        val stringId: Int = application.applicationContext.resources.getIdentifier(
            stringKey.toString(),
            "string",
            application.applicationContext.packageName
        )
        return if (stringId != 0) {
            application.applicationContext.resources.getString(stringId)
        } else {
            "N/A"
        }
    }
}

interface IResourceManager {
    fun getLocalizedString(stringKey: Int): String
}
