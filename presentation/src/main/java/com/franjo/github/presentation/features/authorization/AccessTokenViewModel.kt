package com.franjo.github.presentation.features.authorization

import com.franjo.github.domain.di.MainDispatcher
import com.franjo.github.domain.usecase.GetAccessToken
import com.franjo.github.presentation.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccessTokenViewModel @Inject constructor(
  @MainDispatcher dispatcher: CoroutineDispatcher,
  private val getAccessToken: GetAccessToken
) : BaseViewModel(dispatcher) {

  // Access token after authorization
  fun accessToken(code: String) =
    viewModelScope.launch {
      getAccessToken.execute(code)
    }
}
