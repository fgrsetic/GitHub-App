package com.franjo.github.data.di

import android.app.Application
import androidx.paging.PagingData
import com.franjo.github.data.network.service.GitHubPublicUserApiService
import com.franjo.github.data.network.service.GitHubPrivateUserApiService
import com.franjo.github.data.repository.*
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.*
import com.franjo.github.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesGithubSearchRepositoryImpl(
        gitHubPublicUserApiService: GitHubPublicUserApiService
    ): IGithubSearchRepository<Flow<PagingData<Repo>>> =
        GithubSearchRepositoryImpl(gitHubPublicUserApiService)

    @Provides
    @Singleton
    fun providesUserRepositoryImpl(
        dispatcher: DispatcherProvider,
        gitHubPublicUserApiService: GitHubPublicUserApiService
    ): IUserRepository = UserRepositoryImpl(dispatcher, gitHubPublicUserApiService)

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(
        app: Application
    ): ILoginRepository = LoginRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideAuthenticationRepositoryImpl(
        apiServicePublicUser: GitHubPublicUserApiService,
        privateUserApiService: GitHubPrivateUserApiService,
        encryptedPrefs: IEncryptedPrefs
    ): IAuthenticationRepository =
        AuthenticationRepositoryImpl(apiServicePublicUser, privateUserApiService, encryptedPrefs)

    @Provides
    @Singleton
    fun provideSharedPrefs(githubApplication: Application): ISharedPrefs =
        SharedPrefsImpl(
            githubApplication
        )

    @Provides
    @Singleton
    fun provideEncryptedPrefs(app: Application): IEncryptedPrefs =
        EncryptedSharedPrefsImpl(app)

}