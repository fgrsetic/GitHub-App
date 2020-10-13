package com.franjo.github.presentation.util

import com.franjo.github.presentation.R
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import javax.inject.Inject

class UserDataPresentationMapper @Inject constructor(
    private val resourceManager: IResourceManager
) {

    fun getDataForPresentationUI(userUI: UserUI): List<UserDataRowItem> {
        return listOf(UserDataRowItem(
                resourceManager.getLocalizedString(R.string.username),
                userUI.name
            ),
            UserDataRowItem(
                resourceManager.getLocalizedString(R.string.company),
                userUI.company
            ),
            UserDataRowItem(
                resourceManager.getLocalizedString(R.string.blog),
                userUI.blog
            ),
            UserDataRowItem(
                resourceManager.getLocalizedString(R.string.location),
                userUI.location
            ))
    }

}