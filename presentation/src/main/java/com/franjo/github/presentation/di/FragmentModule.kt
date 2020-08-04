package com.franjo.github.presentation.di

import com.franjo.github.presentation.features.repository_details.RepositoryDetailsFragment
import com.franjo.github.presentation.features.search.SearchRepositoryFragment
import com.franjo.github.presentation.features.search.SortDialogFragment
import com.franjo.github.presentation.features.user.private_user.PrivateUserFragment
import com.franjo.github.presentation.features.user.public_user.UserDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    @FragmentScope
    internal abstract fun contributeSearchRepositoryFragment(): SearchRepositoryFragment

    @ContributesAndroidInjector
    @FragmentScope
    internal abstract fun contributesRepositoryDetailsFragment(): RepositoryDetailsFragment

    @ContributesAndroidInjector
    @FragmentScope
    internal abstract fun contributesSortDialogFragment(): SortDialogFragment

    @ContributesAndroidInjector
    @FragmentScope
    internal abstract fun contributesUserDetailsFragment(): UserDetailsFragment

    @ContributesAndroidInjector
    @FragmentScope
    internal abstract fun contributeLoginFragment(): PrivateUserFragment

    @Scope
    @MustBeDocumented
    internal annotation class FragmentScope
}