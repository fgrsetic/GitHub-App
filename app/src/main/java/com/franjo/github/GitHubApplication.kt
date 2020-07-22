package com.franjo.github

import android.app.Application
import com.franjo.github.di.AppComponent
import com.franjo.github.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class GitHubApplication : Application(), HasAndroidInjector {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }
}

