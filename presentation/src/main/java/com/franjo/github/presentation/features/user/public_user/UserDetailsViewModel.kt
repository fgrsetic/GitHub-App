package com.franjo.github.presentation.features.user.public_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.LoadingApiStatus
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.UserDataPresentation
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserDetailsViewModel @Inject constructor(
    repository: RepositoryUI,
    dispatcherProvider: DispatcherProvider,
    private val userDataPresentation: UserDataPresentation,
    private val getUserData: GetUserData
) :
    BaseViewModel(dispatcherProvider) {

    private val _userRowData = MutableLiveData<List<UserDataRowItem>>()
    val userRowData: LiveData<List<UserDataRowItem>> get() = _userRowData

    private val _userData = MutableLiveData<List<UserUI>>()
    val userData: LiveData<List<UserUI>> get() = _userData

    private val _status = MutableLiveData<LoadingApiStatus>()
    val status: LiveData<LoadingApiStatus> get() = _status

    private val _userRepo = MutableLiveData<RepositoryUI>()
    val userRepo: LiveData<RepositoryUI> get() = _userRepo

    init {
        _userRepo.value = repository
    }


    fun getUserData(query: String) {
        viewModelScope.launch {
            try {
                _status.value = LoadingApiStatus.LOADING
                val result = getUserData.execute(query).asPresentationModel()
                _userData.value = listOf(result)
                _status.value = LoadingApiStatus.DONE
                _userRowData.value = userDataPresentation.getDataForPresentationUI(result)
            } catch (e: Exception) {
                _status.value = LoadingApiStatus.ERROR
                _userRowData.value = ArrayList()
            }
        }
    }
}