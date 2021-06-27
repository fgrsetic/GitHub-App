package com.franjo.github.presentation.features.authorization

import android.net.Uri
import com.franjo.github.domain.shared.ErrorModel

sealed class AuthorizationState {
  data class GetAccessCode(val uri: Uri) : AuthorizationState()
  object Loading : AuthorizationState()
  data class Error(val error: ErrorModel) : AuthorizationState()
  object Success : AuthorizationState()
}
