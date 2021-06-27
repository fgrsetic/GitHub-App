package com.franjo.github.data.di

import com.franjo.github.data.BuildConfig
import com.franjo.github.data.dataSource.network.mapper.NetworkResponseMapper
import com.franjo.github.data.dataSource.network.utils.NetworkUtil
import com.franjo.github.data.dataSource.network.service.Api.BASE_URL
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.data.dataSource.network.service.AuthorizationInterceptor
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  // Moshi object that Retrofit will be using with Kotlin adapter for full Kotlin compatibility
  @Provides
  @Singleton
  fun providesMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  fun providesNetworkErrorMapper() = NetworkResponseMapper()


  @Provides
  @Singleton
  fun providesNetworkResponseHelper(networkErrorMapper: NetworkResponseMapper): NetworkUtil {
    return NetworkUtil(networkErrorMapper)
  }

  // OkHttp logging messages -> verbose logcat
  @Provides
  @Singleton
  fun providesLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.HEADERS
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
  }

  @Provides
  @Singleton
  fun providesHeaderInterceptor(encryptedPrefs: IEncryptedPrefs): Interceptor =
    AuthorizationInterceptor(encryptedPrefs)

  @Provides
  @Singleton
  fun providesOkHttpClient(
    authorizationInterceptor: AuthorizationInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    val builder = OkHttpClient.Builder().addInterceptor(authorizationInterceptor)
    if (BuildConfig.DEBUG) {
      builder.addInterceptor(httpLoggingInterceptor)
    }
    return builder.build()
  }

  // Retrofit class generates an implementation of the GitHubPublicUserApiService interface
  @Provides
  @Singleton
  fun providesRetrofitPublic(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  fun providesPrivateApiService(retrofit: Retrofit): GitHubApiService =
    retrofit.create(GitHubApiService::class.java)
}
