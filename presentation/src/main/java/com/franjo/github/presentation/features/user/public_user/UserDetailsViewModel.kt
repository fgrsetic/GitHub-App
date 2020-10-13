package com.franjo.github.presentation.features.user.public_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.LoadingApiStatus
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.UserDataPresentationMapper
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserDetailsViewModel @Inject constructor(
    repository: RepositoryUI,
    dispatcherProvider: DispatcherProvider,
    private val mapper: UserDataPresentationMapper,
    private val getUserData: GetUserData
) :
    BaseViewModel(dispatcherProvider) {

    private val _userRowData = MutableLiveData<List<UserDataRowItem>>()
    val userRowData: LiveData<List<UserDataRowItem>> get() = _userRowData

    private val _userData = MutableLiveData<UserUI>()
    val userData: LiveData<UserUI> get() = _userData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _status = MutableLiveData<LoadingApiStatus>()
    val status: LiveData<LoadingApiStatus> get() = _status

    private val _userRepo = MutableLiveData<RepositoryUI>()
    val userRepo: LiveData<RepositoryUI> get() = _userRepo

    init {
        _userRepo.value = repository
    }


    fun getUserData(query: String) {
        viewModelScope.launch {
            _status.value = LoadingApiStatus.LOADING
            when (val result = getUserData.execute(query)) {
                is ResultWrapper.Success -> {
                    if (result.data != null) {
                        val userUI = result.data!!.asPresentationModel()
                        _userData.value = userUI
                        _userRowData.value = mapper.getDataForPresentationUI(userUI)
                    }
                    _status.value = LoadingApiStatus.DONE
                }
                is ResultWrapper.Error -> {
                    _error.value = result.error.message
                    _userRowData.value = ArrayList()
                    _status.value = LoadingApiStatus.DONE
                    _status.value = LoadingApiStatus.ERROR
                }
            }

        }
    }
}