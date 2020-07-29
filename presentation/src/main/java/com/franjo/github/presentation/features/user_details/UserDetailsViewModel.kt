package com.franjo.github.presentation.features.user_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.IResourceManager
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoadingApiStatus { LOADING, ERROR, DONE }

class UserDetailsViewModel @Inject constructor(
    repository: RepositoryUI,
    dispatcherProvider: DispatcherProvider,
    private val resourcesManager: IResourceManager,
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
                _userRowData.value = getDataForPresentationUI(result)
            } catch (e: Exception) {
                _status.value = LoadingApiStatus.ERROR
                _userRowData.value = ArrayList()
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