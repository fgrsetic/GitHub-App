package com.franjo.github.presentation.features.user_details.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.util.IResourceManager

class UserDetailsViewModelFactory (
    private val repository: RepositoryUI,
    private val dispatcherProvider: DispatcherProvider,
    private val resourcesManager: IResourceManager,
    private val getUserData: GetUserData
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            return UserDetailsViewModel(
                repository,
                dispatcherProvider,
                resourcesManager,
                getUserData
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
