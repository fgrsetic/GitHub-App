package com.franjo.github.di

import com.franjo.github.presentation.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    @FragmentScope
    abstract fun contributeSearchFragment(): MainFragment


    @Scope
    @MustBeDocumented
    annotation class FragmentScope
}