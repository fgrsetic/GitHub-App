package com.franjo.github.presentation.util

import android.app.Application
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import javax.inject.Inject

class UserDataPresentation @Inject constructor(val app: Application) {

    fun getDataForPresentationUI(userUI: UserUI): List<UserDataRowItem> {
        val resourcesManager = AndroidResourceManager(app)
        val result = mutableListOf<UserDataRowItem>()
        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString("username").toString(),
                userUI.name
            )
        )
        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString("company").toString(),
                userUI.company
            )
        )

        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString("blog").toString(),
                userUI.blog
            )
        )

        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString("location").toString(),
                userUI.location
            )
        )

        result.add(
            UserDataRowItem(
                resourcesManager.getLocalizedString("email").toString(),
                userUI.email
            )
        )
        return result
    }
}