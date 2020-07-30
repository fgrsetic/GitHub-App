package com.franjo.github.presentation.features.user_details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentUserDetailsBinding
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.util.AndroidResourceManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_user_details

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Inject
    lateinit var userData: GetUserData

    @Inject
    lateinit var resourceManager: AndroidResourceManager

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var repository: RepositoryUI

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repository = UserDetailsFragmentArgs.fromBundle(requireArguments()).repository
        val modelFactory =
            UserDetailsViewModelFactory(repository, dispatcherProvider, resourceManager, userData)
        viewModel = ViewModelProvider(this, modelFactory).get(UserDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getUserData(repository.author)

        binding.rvUserDetails.adapter = UserDetailsAdapter()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_go_to_web -> viewModel.userData.value?.get(0)?.let { openBrowser(it) }
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