package com.franjo.github.presentation.di

import androidx.lifecycle.ViewModel
import com.franjo.github.presentation.features.user_details.private_user.PrivateUserViewModel
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
    @ViewModelKey(PrivateUserViewModel::class)
    internal abstract fun privateUserViewModel(viewModel: PrivateUserViewModel): ViewModel
}