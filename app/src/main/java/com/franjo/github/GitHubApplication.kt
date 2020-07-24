package com.franjo.github

import android.app.Application
import com.franjo.github.di.ApplicationComponent
import com.franjo.github.di.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class GitHubApplication : Application(), HasAndroidInjector {

    private lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): DispatchingAndroidInjector<Any>? = androidInjector

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().application(this).create()
        applicationComponent.inject(this)
    }
}

