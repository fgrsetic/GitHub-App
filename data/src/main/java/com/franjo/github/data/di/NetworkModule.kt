package com.franjo.github.data.di

import com.franjo.github.data.BuildConfig
import com.franjo.github.data.network.service.*
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
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
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    // Moshi object that Retrofit will be using with Kotlin adapter for full Kotlin compatibility
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    @Named("OkHttp_1")
    fun provideOkHttpClient1(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }


    // OkHttp logging messages -> verbose logcat
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    // Retrofit class generates an implementation of the GitHubApiService interface
    @Provides
    @Singleton
    @Named("retrofit_one")
    fun provideRetrofit1(@Named("OkHttp_1") okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_SEARCH)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService1( @Named("retrofit_one") retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }


    @Provides
    @Singleton
    @Named("OkHttp_2")
    fun provideOkHttpClient2(
        headerInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    // Send token with application/json in header
    @Provides
    @Singleton
    fun provideEncodingInterceptor2(encryptedPrefs: IEncryptedPrefs): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            // Send access token for authorization
            val token = encryptedPrefs.getValue(ACCESS_TOKEN_KEY, "")

            val newRequest = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "token $token")
                .build()

            return@Interceptor chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    @Named("retrofit_two")
    fun provideRetrofit2(@Named("OkHttp_2") okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_PRIVATE_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService2( @Named("retrofit_two") retrofit: Retrofit): GitHubApiService2 {
        return retrofit.create(GitHubApiService2::class.java)
    }

}