package com.franjo.github.presentation.features.user.private_user

import android.os.Bundle
import android.view.Menu
import android.view.View
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentPrivateUserBinding
import com.franjo.github.presentation.features.user.public_user.UserDetailsAdapter
import javax.inject.Inject

class PrivateUserFragment : BaseFragment<FragmentPrivateUserBinding>() {

  override fun getFragmentView(): Int = R.layout.fragment_private_user

  @Inject
  lateinit var viewModel: PrivateUserViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.viewModel = viewModel

    viewModel.loadPrivateUser()

    binding.rvUserDetails.adapter =
      UserDetailsAdapter()
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    super.onPrepareOptionsMenu(menu)
    menu.findItem(R.id.actionWeb).isVisible = false
    menu.findItem(R.id.actionLogin).isVisible = false
    menu.findItem(R.id.actionPrivateUser).isVisible = false
  }
}
