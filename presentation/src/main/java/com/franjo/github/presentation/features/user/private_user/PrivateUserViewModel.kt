package com.franjo.github.presentation.features.user.private_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.LoadingApiStatus
import com.franjo.github.domain.usecase.GetAuthenticatedUser
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.UserDataPresentation
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrivateUserViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getAuthenticatedUser: GetAuthenticatedUser,
    private val userDataPresentation: UserDataPresentation
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
                _userList.value = userUIResult?.let { userDataPresentation.getDataForPresentationUI(it) }
            } catch (e: Exception) {
                _status.value = LoadingApiStatus.ERROR
            }
        }
    }
}