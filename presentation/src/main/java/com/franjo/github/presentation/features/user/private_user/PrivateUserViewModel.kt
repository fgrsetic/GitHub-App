package com.franjo.github.presentation.features.user.private_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.di.MainDispatcher
import com.franjo.github.domain.usecase.GetAuthenticatedUser
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.UserDataRowItem
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.LoadingApiStatus
import com.franjo.github.presentation.util.UserDataPresentationMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrivateUserViewModel @Inject constructor(
  @MainDispatcher dispatcher: CoroutineDispatcher,
  private val getAuthenticatedUser: GetAuthenticatedUser,
  private val mapper: UserDataPresentationMapper
) : BaseViewModel(dispatcher) {

  private val _status = MutableLiveData<LoadingApiStatus>()
  val status: LiveData<LoadingApiStatus> get() = _status

  private val _userList = MediatorLiveData<List<UserDataRowItem>>()
  val userList: LiveData<List<UserDataRowItem>> get() = _userList

  fun loadPrivateUser() {
    viewModelScope.launch {
      try {
        _status.value = LoadingApiStatus.LOADING
        val userUIResult = getAuthenticatedUser.execute().asPresentationModel()
        _status.value = LoadingApiStatus.DONE
        _userList.value = userUIResult.let { mapper.getDataForPresentationUI(it) }
      } catch (e: Exception) {
        _status.value = LoadingApiStatus.ERROR
      }
    }
  }
}
