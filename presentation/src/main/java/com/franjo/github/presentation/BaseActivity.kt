package com.franjo.github.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.franjo.github.presentation.databinding.ActivityBaseBinding
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

// This is a single activity application that uses the Navigation library.
// Content is displayed by Fragments
class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var binding: ActivityBaseBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)

        setSupportActionBar(toolbar)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.searchRepositoryFragment,
            R.id.repositoryDetailsFragment,
            R.id.userDetailsFragment
        ).build()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp())
    }
}