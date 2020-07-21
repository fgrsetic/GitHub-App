package com.franjo.github.domain.shared

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object DispatcherProvider {

    fun provideUIContext(): CoroutineContext = Dispatchers.Main

    fun provideComputationContext(): CoroutineContext = Dispatchers.Default

    fun provideIOContext(): CoroutineContext = Dispatchers.IO

}