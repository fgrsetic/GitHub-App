package com.franjo.github.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GetAccessToken
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.features.user_details.LoadingApiStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getAccessToken: GetAccessToken
) : BaseViewModel(dispatcherProvider) {


    private val _status = MutableLiveData<LoadingApiStatus>()
    val status: LiveData<LoadingApiStatus> get() = _status


    fun accessToken(code: String) = viewModelScope.launch {
        _status.value = LoadingApiStatus.LOADING
        when (getAccessToken.execute(code)) {
            is ResultWrapper.Success -> {
                _status.value = LoadingApiStatus.DONE
            }
            is ResultWrapper.Error -> _status.value = LoadingApiStatus.ERROR
        }
    }
}