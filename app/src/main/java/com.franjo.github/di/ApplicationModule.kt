package com.franjo.github.di

import android.content.Context
import com.franjo.github.App
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class ApplicationModule {

  @ExperimentalCoroutinesApi
  @Provides
  @Singleton
  @ApplicationContext
  fun provideApplication(app: App): Context = app
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext
