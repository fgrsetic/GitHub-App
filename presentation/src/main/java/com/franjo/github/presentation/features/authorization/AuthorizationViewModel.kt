package com.franjo.github.presentation.features.authorization

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.di.MainDispatcher
import com.franjo.github.domain.model.authorization.AuthorizationUrl
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GenerateAuthorizationUrl
import com.franjo.github.domain.usecase.LoginUser
import com.franjo.github.presentation.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CLIENT_ID_PARAM = "client_id"
const val REDIRECT_URL_PARAM = "redirect_url"
const val ACCESS_CODE = "code"

class AuthorizationViewModel @Inject constructor(
  @MainDispatcher dispatcher: CoroutineDispatcher,
  private val generateAuthorizationUrl: GenerateAuthorizationUrl,
  private val loginUser: LoginUser
) : BaseViewModel(dispatcher) {

  private val _state = MutableLiveData<AuthorizationState>()
  val state: LiveData<AuthorizationState> get() = _state


  fun startAuthorizationProcess() {
    _state.value = setAuthorizationState(generateAuthorizationUrl())
  }

  fun authorize(uriWithCode: Uri){
    viewModelScope.launch {
      _state.value = AuthorizationState.Loading
      val result = loginUser(getAccessCode(uriWithCode))
      _state.value = when (result) {
        is ResultWrapper.Success -> AuthorizationState.Success
        is ResultWrapper.Error -> AuthorizationState.Error(result.error)
      }
    }
  }

  private fun setAuthorizationState(url: AuthorizationUrl) = AuthorizationState.GetAccessCode(
    Uri.parse(url.url)
      .buildUpon()
      .appendQueryParameter(CLIENT_ID_PARAM, url.clientId)
      .appendQueryParameter(REDIRECT_URL_PARAM, url.redirectUrl)
      .build()
  )

  private fun getAccessCode(uri: Uri) = uri.getQueryParameter(ACCESS_CODE).orEmpty()

}


