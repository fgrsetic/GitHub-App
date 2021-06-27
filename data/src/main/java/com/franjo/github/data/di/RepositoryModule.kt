package com.franjo.github.data.di

import android.app.Application
import androidx.paging.PagingData
import com.franjo.github.data.dataSource.network.UserRemoteDataSource
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.data.dataSource.network.utils.AuthorizationUtil
import com.franjo.github.data.dataSource.network.utils.NetworkUtil
import com.franjo.github.data.repository.*
import com.franjo.github.domain.di.IODispatcher
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

  @Provides
  @Singleton
  fun providesUserRepositoryImpl(
    dataSource: UserRemoteDataSource
  ): IUserRepository = UserRepositoryImpl(dataSource)

  @Provides
  @Singleton
  fun providesAuthorizationUtil(): AuthorizationUtil =
    AuthorizationUtil()

  @Provides
  @Singleton
  fun provideAuthorizationRepositoryImpl(
    apiService: GitHubApiService,
    @IODispatcher dispatcher: CoroutineDispatcher,
    networkUtil: NetworkUtil,
    authorizationUtil: AuthorizationUtil,
    encryptedPrefs: IEncryptedPrefs
  ): IAuthorizationRepository =
    AuthorizationRepositoryImpl(
      apiService,
      dispatcher,
      networkUtil,
      authorizationUtil,
      encryptedPrefs
    )

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
