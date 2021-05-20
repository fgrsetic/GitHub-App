package com.franjo.github.presentation.features.login

import androidx.lifecycle.ViewModel
import com.franjo.github.domain.usecase.GetLogin
import javax.inject.Inject

class LoginViewModel @Inject constructor(
  private val getLogin: GetLogin
) : ViewModel() {

  // Login to GitHub for authorization
  fun startLoginFlow() {
    getLogin.login()
  }
}
