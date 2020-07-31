package com.franjo.github.data.di

import com.franjo.github.data.BuildConfig
import com.franjo.github.data.network.service.ACCESS_TOKEN_KEY
import com.franjo.github.data.network.service.BASE_URL
import com.franjo.github.data.network.service.GitHubApiService
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

    @Provides
    fun provideOkHttpClient(
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

    // Moshi object that Retrofit will be using with Kotlin adapter for full Kotlin compatibility
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // OkHttp logging messages -> verbose logcat
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    // Send token with application/json in header
    @Provides
    fun provideEncodingInterceptor(encryptedPrefs: IEncryptedPrefs): Interceptor {
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

    // Retrofit class generates an implementation of the GitHubApiService interface
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GitHubApiService {
        return retrofit.create(GitHubApiService::class.java)
    }

}