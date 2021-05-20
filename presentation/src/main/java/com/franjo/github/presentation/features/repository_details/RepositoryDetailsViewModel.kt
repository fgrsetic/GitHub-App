package com.franjo.github.presentation.features.repository_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.util.Event
import javax.inject.Inject

class RepositoryDetailsViewModel @Inject constructor(
  repository: RepositoryUI
) : ViewModel() {

  // Selected Repository LiveData, and initialize during init
  private val _selectedRepo = MutableLiveData<RepositoryUI>()
  val selectedRepo: LiveData<RepositoryUI> get() = _selectedRepo

  private val _navigateToUserDetails by lazy { MutableLiveData<Event<RepositoryUI>>() }
  val navigateToUserDetails: LiveData<Event<RepositoryUI>> get() = _navigateToUserDetails

  init {
    _selectedRepo.value = repository
  }

  fun onUserThumbnailClicked(repository: RepositoryUI) {
    _navigateToUserDetails.value = Event(repository)
  }
}
