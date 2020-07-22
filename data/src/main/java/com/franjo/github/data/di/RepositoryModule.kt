package com.franjo.github.data.di

import com.franjo.github.data.network.service.RestApiInterface
import com.franjo.github.data.repository.GithubRepository
import com.franjo.github.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteRepositoryImpl(
        dispatcherProvider: DispatcherProvider,
        restApiInterface: RestApiInterface
    ) = GithubRepository(
        dispatcherProvider, restApiInterface
    )
}