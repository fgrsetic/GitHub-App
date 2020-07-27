package com.franjo.github.data.di

import android.app.Application
import com.franjo.github.data.service.SharedPrefsServiceImpl
import com.franjo.github.domain.service.ISharedPrefsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {
    @Provides
    @Singleton
    fun provideSharedPrefsService(githubApplication: Application): ISharedPrefsService =
        SharedPrefsServiceImpl(githubApplication)

}

