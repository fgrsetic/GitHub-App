package com.franjo.github.data.di

import android.app.Application
import androidx.paging.PagingData
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.network.service.GitHubApiService2
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
        dispatcher: DispatcherProvider,
        gitHubApiService: GitHubApiService
    ): IGithubSearchRepository<Flow<PagingData<Repo>>> =
        GithubSearchRepositoryImpl(gitHubApiService)

    @Provides
    @Singleton
    fun providesUserRepositoryImpl(
        dispatcher: DispatcherProvider,
        gitHubApiService: GitHubApiService
    ): IUserRepository = UserRepositoryImpl(dispatcher, gitHubApiService)

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(
        app: Application
    ): ILoginRepository = LoginRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideAuthenticationRepositoryImpl(
        dispatcherProvider: DispatcherProvider,
        apiService: GitHubApiService,
        apiService2: GitHubApiService2,
        encryptedPrefs: IEncryptedPrefs
    ): IAuthenticationRepository =
        AuthenticationRepositoryImpl(apiService, apiService2, encryptedPrefs)

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