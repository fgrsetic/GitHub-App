package com.franjo.github.di

import android.app.Application
import com.franjo.github.App
import com.franjo.github.data.di.NetworkModule
import com.franjo.github.data.di.RepositoryModule
import com.franjo.github.domain.di.DispatcherModule
import com.franjo.github.presentation.di.ActivityModule
import com.franjo.github.presentation.di.AndroidResourceModule
import com.franjo.github.presentation.di.FragmentModule
import com.franjo.github.presentation.di.SaveStateHandleModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
  modules = [
    // binds Android base types (Activities, Fragments, etc.)
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ActivityModule::class,
    FragmentModule::class,
    DispatcherModule::class,
    SaveStateHandleModule::class,
    AndroidResourceModule::class
  ]
)
interface ApplicationComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance application: Application): ApplicationComponent
  }

  fun inject(app: App)
}
