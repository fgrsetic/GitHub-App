package com.franjo.github.domain.di

import com.franjo.github.domain.shared.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider() = DispatcherProvider

}