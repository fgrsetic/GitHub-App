package com.franjo.github.presentation.features.user.public_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.util.UserDataPresentationMapper
import kotlinx.coroutines.CoroutineDispatcher

class UserDetailsViewModelFactory(
  private val repository: RepositoryUI,
  private val dispatcher: CoroutineDispatcher,
  private val userDataPresentationMapper: UserDataPresentationMapper,
  private val getUserData: GetUserData
) : ViewModelProvider.Factory {

  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
      return UserDetailsViewModel(
        repository,
        dispatcher,
        userDataPresentationMapper,
        getUserData
      ) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
