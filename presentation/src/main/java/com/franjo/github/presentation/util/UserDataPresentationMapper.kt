package com.franjo.github.presentation.util

import com.franjo.github.presentation.R
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import javax.inject.Inject

class UserDataPresentationMapper @Inject constructor(
    private val resourceManager: IResourceManager
) {

    fun getDataForPresentationUI(userUI: UserUI?): List<UserDataRowItem> {
        val result = mutableListOf<UserDataRowItem>()
        userUI?.let {
            result.add(viewModelFor(R.string.username, userUI.name))
            result.add(viewModelFor(R.string.company, userUI.company))
            result.add(viewModelFor(R.string.blog, userUI.blog))
            result.add(viewModelFor(R.string.location, userUI.location))
            result.add(viewModelFor(R.string.email, userUI.email))
        }
        return result
    }

    private fun viewModelFor(labelResId: Int, value: String): UserDataRowItem {
        return UserDataRowItem(
            label = resourceManager.getLocalizedString(labelResId),
            description = value
        )
    }

}