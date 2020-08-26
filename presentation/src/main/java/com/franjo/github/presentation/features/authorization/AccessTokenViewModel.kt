package com.franjo.github.presentation.features.authorization

import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetAccessToken
import com.franjo.github.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccessTokenViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getAccessToken: GetAccessToken
): BaseViewModel(dispatcherProvider) {


    // Access token after authorization
    fun accessToken(code: String) =
        viewModelScope.launch {
            getAccessToken.execute(code)
        }

}