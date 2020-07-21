package com.franjo.github.data.di

import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteRepositoryImpl() = RemoteRepositoryImpl(
        dispatcherProvider,
        restApiInterface
    )
}