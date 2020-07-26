package com.franjo.github.data.di

import androidx.paging.PagingData
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.repository.GitHubRepository
import com.franjo.github.domain.model.Repo
import com.franjo.github.domain.repository.IGithubRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepositoryImpl(
        gitHubApiService: GitHubApiService
    ): IGithubRepository<Flow<PagingData<Repo>>> = GitHubRepository(gitHubApiService)
}