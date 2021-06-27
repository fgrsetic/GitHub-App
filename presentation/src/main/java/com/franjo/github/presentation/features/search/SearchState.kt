package com.franjo.github.presentation.features.search

import com.franjo.github.domain.shared.ErrorModel
import com.franjo.github.presentation.model.RepositoryUI

sealed class SearchState {
  object Init : SearchState()
  object HideKeyboard : SearchState()
  data class Search(val query: String): SearchState()
  data class User(val userLoggedIn: Boolean) : SearchState()
  data class Error(val error: ErrorModel) : SearchState()
  data class NavigateToDetails(val repositoryUI: RepositoryUI) : SearchState()
  data class NavigateToUserDetails(val repositoryUI: RepositoryUI) : SearchState()
  data class NavigateToPrivateUserDetails(val isPrivateUser: Boolean) : SearchState()
  data class NavigateToAuthorization(val shouldAuthorize: Boolean) : SearchState()
}