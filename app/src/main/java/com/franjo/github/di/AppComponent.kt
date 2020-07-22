package com.franjo.github.di

import android.app.Application
import com.franjo.github.GitHubApplication
import com.franjo.github.data.di.NetworkModule
import com.franjo.github.data.di.RepositoryModule
import com.franjo.github.data.di.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@SuppressWarnings("unchecked")
@Singleton
@Component(
    modules = [
        NetworkModule::class,
        UseCaseModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        FragmentModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApplication: GitHubApplication)
}