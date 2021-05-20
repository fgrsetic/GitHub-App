package com.franjo.github.presentation.di

import com.franjo.github.presentation.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

  @ContributesAndroidInjector(modules = [FragmentModule::class])
  internal abstract fun contributesBaseActivity(): BaseActivity
}
