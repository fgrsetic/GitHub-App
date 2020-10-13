package com.franjo.github.data.di

import android.app.Application
import androidx.paging.PagingData
import com.franjo.github.data.dataSource.UserRemoteDataSource
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.repository.*
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesGithubSearchRepositoryImpl(
        apiService: GitHubApiService
    ): IGithubSearchRepository<Flow<PagingData<Repo>>> =
        GithubSearchRepositoryImpl(apiService)

    @FlowPreview
    @Provides
    @Singleton
    fun providesUserRepositoryImpl(
        dataSource: UserRemoteDataSource
    ): IUserRepository = UserRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideLoginRepositoryImpl(
        app: Application
    ): ILoginRepository = LoginRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideAuthenticationRepositoryImpl(
        apiService: GitHubApiService,
        encryptedPrefs: IEncryptedPrefs
    ): IAuthenticationRepository =
        AuthenticationRepositoryImpl(apiService, encryptedPrefs)

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