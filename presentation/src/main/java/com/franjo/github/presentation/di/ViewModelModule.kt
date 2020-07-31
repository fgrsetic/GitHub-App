package com.franjo.github.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.presentation.features.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

//    @Binds
//    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
////
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchRepositoryViewModel::class)
//    internal abstract fun searchRepositoryViewModel(viewModel: SearchRepositoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel
}