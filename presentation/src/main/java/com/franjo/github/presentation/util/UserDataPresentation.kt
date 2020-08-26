package com.franjo.github.presentation.util

import android.app.Application
import com.franjo.github.presentation.R
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import javax.inject.Inject

class UserDataPresentation @Inject constructor(val app: Application) {

    fun getDataForPresentationUI(userUI: UserUI): List<UserDataRowItem> {
        val resourcesManager = AndroidResourceManager(app)
        val result = mutableListOf<UserDataRowItem>()
        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString(R.string.username.toString()).toString(),
                userUI.name
            )
        )
        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString(R.string.company.toString()).toString(),
                userUI.company
            )
        )

        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString(R.string.blog.toString()).toString(),
                userUI.blog
            )
        )

        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString(R.string.location.toString()).toString(),
                userUI.location
            )
        )

        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString(R.string.email.toString()).toString(),
                userUI.email
            )
        )
        return result
    }
}