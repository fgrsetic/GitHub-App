package com.franjo.github.di

import android.content.Context
import com.franjo.github.GitHubApplication
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplication(application: GitHubApplication): Context = application
}

/** `Dagger2` qualifier that describes a [android.content.Context]
 * instance as an application context.  */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext