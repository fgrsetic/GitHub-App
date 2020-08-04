package com.franjo.github.presentation.features.user.public_user

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentUserDetailsBinding
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.util.UserDataPresentation
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_user_details


    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    lateinit var userData: GetUserData

    @Inject
    lateinit var userDataPresentation: UserDataPresentation

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var repository: RepositoryUI

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repository = UserDetailsFragmentArgs.fromBundle(requireArguments()).repository
        val modelFactory =
            UserDetailsViewModelFactory(
                repository,
                dispatcherProvider,
                userDataPresentation,
                userData
            )
        viewModel = ViewModelProvider(this, modelFactory).get(UserDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getUserData(repository.author)

        binding.rvUserDetails.adapter =
            UserDetailsAdapter()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.actionWeb).isVisible = true
        menu.findItem(R.id.actionLogin).isVisible = false
        menu.findItem(R.id.actionPrivateUser).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionWeb -> viewModel.userData.value?.get(0)?.let { openBrowser(it) }
        }
        return true
    }

    private fun openBrowser(user: UserUI) {
        if (Patterns.WEB_URL.matcher(user.url).matches()) {
            user.url.let { url ->
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                requireContext().startActivity(i)
            }
        }
    }
}