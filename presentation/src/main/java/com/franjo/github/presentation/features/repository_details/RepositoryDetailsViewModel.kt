package com.franjo.github.presentation.features.repository_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franjo.github.presentation.model.RepositoryUI
import javax.inject.Inject

class RepositoryDetailsViewModel @Inject constructor(
    repository: RepositoryUI
) : ViewModel() {

    // Selected Repository LiveData, and initialize during init
    private val _selectedRepo = MutableLiveData<RepositoryUI>()
    val selectedRepo: LiveData<RepositoryUI> get() = _selectedRepo

    private val _navigateToUserDetails by lazy { MutableLiveData<RepositoryUI>() }
    val navigateToUserDetails: LiveData<RepositoryUI> get() = _navigateToUserDetails

    init {
        _selectedRepo.value = repository
    }

    fun toUserDetailsNavigate(repository: RepositoryUI) {
        _navigateToUserDetails.value = repository
    }

    fun onUserDetailsNavigated() {
        _navigateToUserDetails.value = null
    }

}