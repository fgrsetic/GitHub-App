package com.franjo.github.presentation.di

import android.app.Application
import com.franjo.github.presentation.util.AndroidResourceManager
import com.franjo.github.presentation.util.IResourceManager
import dagger.Module
import dagger.Provides

@Module
class AndroidResourceModule {

  @Provides
  fun provideAndroidResourceManagerImpl(app: Application): IResourceManager =
    AndroidResourceManager(app)
}
