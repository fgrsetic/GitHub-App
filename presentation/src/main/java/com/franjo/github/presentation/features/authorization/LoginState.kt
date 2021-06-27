package com.franjo.github.presentation.features.authorization

sealed class LoginState {

  data class User(val isUserLoggedIn: Boolean): LoginState()
}
