package com.franjo.github.data.di

import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.repository.GitHubRepository
import com.franjo.github.domain.repository.IGithubRepository
import com.franjo.github.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepositoryImpl(
        dispatcherProvider: DispatcherProvider,
        gitHubApiService: GitHubApiService
    ): IGithubRepository = GitHubRepository(
        dispatcherProvider, gitHubApiService
    )
}