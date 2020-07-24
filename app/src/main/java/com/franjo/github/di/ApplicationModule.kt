package com.franjo.github.di

import android.content.Context
import com.franjo.github.GitHubApplication
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplication(application: GitHubApplication): Context = application
}

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext