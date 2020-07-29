package com.franjo.github.presentation

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {

    val queryValue = MutableLiveData<Bundle>()
}