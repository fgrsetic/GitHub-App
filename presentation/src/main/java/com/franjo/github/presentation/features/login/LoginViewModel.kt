package com.franjo.github.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GetAccessToken
import com.franjo.github.domain.usecase.GetAuthenticatedUser
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.features.user_details.LoadingApiStatus
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.AndroidResourceManager
import com.franjo.github.presentation.util.IResourceManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getAccessToken: GetAccessToken,
    private val getAuthenticatedUser: GetAuthenticatedUser,
    private val resourcesManager: AndroidResourceManager
) : BaseViewModel(dispatcherProvider) {


    private val _status = MutableLiveData<LoadingApiStatus>()
    val status: LiveData<LoadingApiStatus> get() = _status

    private val _userList = MutableLiveData<List<UserDataRowItem>>()
    val userList: LiveData<List<UserDataRowItem>> get() = _userList

    fun accessToken(code: String) = viewModelScope.launch {
        _status.value = LoadingApiStatus.LOADING
        when (getAccessToken.execute(code)) {
            is ResultWrapper.Success -> {
                _status.value = LoadingApiStatus.DONE
                viewModelScope.launch {
                    try {
                        val result = getAuthenticatedUser.execute().asPresentationModel()
                        _userList.value = getDataForPresentationUI(result)
                        _status.value = LoadingApiStatus.DONE
                    } catch (e: Exception) {
                        _status.value = LoadingApiStatus.ERROR
                    }
                }.join()
            }
        }
    }

    fun getDataForPresentationUI(userUI: UserUI): List<UserDataRowItem> {
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