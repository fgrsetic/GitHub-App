package com.franjo.github

import android.app.Application
import com.franjo.github.di.ApplicationComponent
import com.franjo.github.di.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class App : Application(), HasAndroidInjector {

  private lateinit var applicationComponent: ApplicationComponent

  @Inject
  lateinit var androidInjector: DispatchingAndroidInjector<Any>
  override fun androidInjector(): DispatchingAndroidInjector<Any> = androidInjector

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      TODO("Create and plant timber tree for logging crashes via FirebaseCrashlytics")
    }

    // Here we use our DaggerApplicationComponent to inject our Application class
    // This way a DispatchingAndroidInjector is injected which is then returned
    // when an injector for an activity is requested through androidInjector
    applicationComponent = DaggerApplicationComponent.factory().create(this)
    applicationComponent.inject(this)
  }
}
