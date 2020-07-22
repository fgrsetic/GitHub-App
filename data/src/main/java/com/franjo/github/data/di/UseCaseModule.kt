package com.franjo.github.data.di

import com.franjo.github.domain.repository.IGithubRepository
import com.franjo.github.domain.usecase.GetSearchedRepositories
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetRepositoryListItems(
        githubRepository: IGithubRepository
    ) = GetSearchedRepositories(githubRepository)

}