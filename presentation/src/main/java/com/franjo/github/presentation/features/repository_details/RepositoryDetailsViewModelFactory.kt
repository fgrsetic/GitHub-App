package com.franjo.github.presentation.features.repository_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.presentation.model.RepositoryUI

// Simple ViewModel factory that provides the Repository and context to the Details ViewModel
class RepositoryDetailsViewModelFactory(
    private val repository: RepositoryUI
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryDetailsViewModel::class.java)) {
            return RepositoryDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
