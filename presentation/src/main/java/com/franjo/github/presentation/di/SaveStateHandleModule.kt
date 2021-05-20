package com.franjo.github.presentation.di

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides

@Module
class SaveStateHandleModule {

  @Provides
  fun provideSaveStateHandle() = SavedStateHandle()
}
