package com.franjo.github.presentation.features.user_details.private_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.LoadingApiStatus
import com.franjo.github.domain.usecase.GetAuthenticatedUser
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.AndroidResourceManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrivateUserViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getAuthenticatedUser: GetAuthenticatedUser,
    private val resourcesManager: AndroidResourceManager
) : BaseViewModel(dispatcherProvider) {


    private val _status = MutableLiveData<LoadingApiStatus>()
    val status: LiveData<LoadingApiStatus> get() = _status

    private val _userList = MediatorLiveData<List<UserDataRowItem>>()
    val userList: LiveData<List<UserDataRowItem>> get() = _userList


    fun loadPrivateUser(token: String?) {
        viewModelScope.launch {
            try {
                _status.value = LoadingApiStatus.LOADING
                val userUIResult = token?.let { getAuthenticatedUser.execute(it).asPresentationModel() }
                _status.value = LoadingApiStatus.DONE
                _userList.value = userUIResult?.let { getDataForPresentationUI(it) }
            } catch (e: Exception) {
                _status.value = LoadingApiStatus.ERROR
            }
        }
    }

    private fun getDataForPresentationUI(userUI: UserUI): List<UserDataRowItem> {
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