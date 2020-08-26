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


    // Public
    @Provides
    @Singleton
    @Named("okHttp_public")
    fun providePublicOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
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

    // Retrofit class generates an implementation of the GitHubPublicUserApiService interface
    @Provides
    @Singleton
    @Named("retrofit_public")
    fun provideRetrofitPublic(@Named("okHttp_public") okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_PUBLIC_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providePublicApiService( @Named("retrofit_public") retrofit: Retrofit): GitHubPublicUserApiService {
        return retrofit.create(GitHubPublicUserApiService::class.java)
    }



    // Private
    @Provides
    @Singleton
    @Named("okHttp_private")
    fun providePrivateOkHttpClient(
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

    @Provides
    @Singleton
    @Named("retrofit_private")
    fun provideRetrofitPrivate(@Named("okHttp_private") okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_PRIVATE_USER)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providePrivateApiService( @Named("retrofit_private") retrofit: Retrofit): GitHubPrivateUserApiService {
        return retrofit.create(GitHubPrivateUserApiService::class.java)
    }

}