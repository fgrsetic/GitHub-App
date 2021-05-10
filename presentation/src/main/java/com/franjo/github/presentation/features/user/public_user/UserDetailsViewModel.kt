package com.franjo.github.presentation.features.user.public_user

import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.LoadingApiStatus
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.UserDataPresentationMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserDetailsViewModel @Inject constructor(
    repository: RepositoryUI,
    dispatcher: CoroutineDispatcher,
    private val mapper: UserDataPresentationMapper,
    val getUserData: GetUserData
) :
    BaseViewModel(dispatcher) {

    val userRowData = MutableLiveData<List<UserDataRowItem>>()
    val userData = MutableLiveData<UserUI>()
    val error = MutableLiveData<String>()
    val status = MutableLiveData<LoadingApiStatus>()
    val userRepo = MutableLiveData<RepositoryUI>()

    init {
        userRepo.postValue(repository)
    }

    fun getUserDataFor(query: String) {
        viewModelScope.launch {
            status.value = LoadingApiStatus.LOADING
            when (val userResult = getUserData(query)) {
                is ResultWrapper.Success -> {
                    val userUI = userResult.data.asPresentationModel()
                    userData.postValue(userUI)
                    userRowData.postValue(mapper.getDataForPresentationUI(userUI))
                    status.value = LoadingApiStatus.DONE
                }
                is ResultWrapper.Error -> {
                    error.postValue(userResult.throwable.message.toString())
                    userRowData.postValue(ArrayList())
                    status.value = LoadingApiStatus.DONE
                    status.value = LoadingApiStatus.ERROR
                }
                else -> throw Exception()
            }
        }
    }
}