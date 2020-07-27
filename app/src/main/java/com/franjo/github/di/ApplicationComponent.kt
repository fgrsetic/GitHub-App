package com.franjo.github.di

import android.app.Application
import com.franjo.github.GitHubApplication
import com.franjo.github.data.di.NetworkModule
import com.franjo.github.data.di.RepositoryModule
import com.franjo.github.data.di.ServiceModule
import com.franjo.github.domain.di.DispatcherModule
import com.franjo.github.presentation.di.ActivityModule
import com.franjo.github.presentation.di.FragmentModule
import com.franjo.github.presentation.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        ActivityModule::class,
        DispatcherModule::class,
        ServiceModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun create(): ApplicationComponent
    }

    fun inject(githubApplication: GitHubApplication)
}