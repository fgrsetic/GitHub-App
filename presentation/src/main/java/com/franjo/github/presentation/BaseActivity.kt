package com.franjo.github.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.franjo.github.presentation.databinding.ActivityBaseBinding
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

// This is a single activity application that uses the Navigation library.
// Content is displayed by Fragments
class BaseActivity : AppCompatActivity() {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  private lateinit var binding: ActivityBaseBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_base)

    //   setSupportActionBar(binding.toolbar)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
//            navController,
//            appBarConfiguration
//        )

    // testCrashlytics()
  }

  @SuppressLint("SetTextI18n")
  private fun testCrashlytics() {
    val crashButton = Button(this)
    crashButton.text = "Test Crashlytics!"
    crashButton.setOnClickListener {
      throw RuntimeException("Test Crash")
    }

    addContentView(
      crashButton,
      ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
      )
    )
  }

  override fun onSupportNavigateUp(): Boolean {
    return (
      Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp() ||
        super.onSupportNavigateUp()
      )
  }

//  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//    menuInflater.inflate(R.menu.menu_main, menu)
//    menu?.findItem(R.id.actionPrivateUser)?.isVisible = true
//    menu?.findItem(R.id.actionLogin)?.isVisible = true
//    return true
//  }

}
