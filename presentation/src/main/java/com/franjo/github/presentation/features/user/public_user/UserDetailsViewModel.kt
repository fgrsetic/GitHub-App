package com.franjo.github.presentation.features.user.public_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.LoadingApiStatus
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

  private val _userRowData = MutableLiveData<List<UserDataRowItem>>()
  val userRowData: LiveData<List<UserDataRowItem>> = _userRowData

  private val _userData = MutableLiveData<UserUI>()
  val userData: LiveData<UserUI> = _userData

  private val _error = MutableLiveData<String>()
  val error: LiveData<String> = _error

  private val _status = MutableLiveData<LoadingApiStatus>()
  val status: LiveData<LoadingApiStatus> get() = _status

  private val _userRepo = MutableLiveData<RepositoryUI>()
  val userRepo: LiveData<RepositoryUI> = _userRepo

  init {
    _userRepo.postValue(repository)
  }

  fun getUserDataFor(author: String) {
    viewModelScope.launch {
      _status.value = LoadingApiStatus.LOADING
      when (val userResult = getUserData(author)) {
        is ResultWrapper.Success -> {
          val userUI = userResult.data.asPresentationModel()
          _userData.value = userUI
          _userRowData.postValue(mapper.getDataForPresentationUI(userUI))
          _status.value = LoadingApiStatus.DONE
        }
        is ResultWrapper.Error -> {
          _error.postValue(userResult.error.toString())
          _userRowData.postValue(ArrayList())
          _status.value = LoadingApiStatus.DONE
          _status.value = LoadingApiStatus.ERROR
        }
        else -> throw Exception()
      }
    }
  }
}
