package com.franjo.github.di

import com.evolutio.presentation.di.FragmentModule
import com.franjo.github.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeBaseActivity(): MainActivity
}