package com.franjo.github.data.di

import androidx.paging.PagingData
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.repository.SearchRepositoryImpl
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.IGithubRepository
import com.franjo.github.domain.repository.IUserRepository
import com.franjo.github.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepositoryImpl1(
        dispatcher: DispatcherProvider,
        gitHubApiService: GitHubApiService
    ): IGithubRepository<Flow<PagingData<Repo>>> =
        SearchRepositoryImpl(dispatcher, gitHubApiService)

    @Provides
    @Singleton
    fun provideRemoteRepositoryImpl2(
        dispatcher: DispatcherProvider,
        gitHubApiService: GitHubApiService
    ): IUserRepository = SearchRepositoryImpl(dispatcher, gitHubApiService)
}