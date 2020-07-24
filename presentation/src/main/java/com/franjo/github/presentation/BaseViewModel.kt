package com.franjo.github.presentation

import androidx.lifecycle.ViewModel
import com.franjo.github.domain.shared.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel(
        dispatcherProvider: DispatcherProvider
) : ViewModel() {

    // This is the job for all coroutines started by ViewModel
    // Cancelling this job will cancel all coroutines started by ViewModel
    private val viewModelJob = SupervisorJob()

    // This is the main scope for all coroutines launched by ViewModel
    // Since we pass viewModelJob, we can cancel all coroutines launched by uiScope by calling viewModelJob.cancel()
    val viewModelScope = CoroutineScope(viewModelJob + dispatcherProvider.provideUIContext())

    // Cancel all coroutines when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}