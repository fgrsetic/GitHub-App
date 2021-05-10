package com.franjo.github.presentation.features.user.public_user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.franjo.github.domain.di.MainDispatcher
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentUserDetailsBinding
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserUI
import com.franjo.github.presentation.util.UserDataPresentationMapper
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_user_details

    @Inject
    @MainDispatcher
    lateinit var dispatcher: CoroutineDispatcher

    @Inject
    lateinit var userData: GetUserData

    @Inject
    lateinit var userDataPresentationMapper: UserDataPresentationMapper

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var repository: RepositoryUI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = UserDetailsFragmentArgs.fromBundle(requireArguments()).repository
        val modelFactory =
            UserDetailsViewModelFactory(
                repository,
                dispatcher,
                userDataPresentationMapper,
                userData
            )
        viewModel = ViewModelProvider(this, modelFactory).get(UserDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.getUserDataFor(repository.author)

        binding.rvUserDetails.apply {
            adapter = UserDetailsAdapter()
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        observeError()
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, error, LENGTH_SHORT).show()
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.actionWeb).isVisible = true
        menu.findItem(R.id.actionLogin).isVisible = false
        menu.findItem(R.id.actionPrivateUser).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionWeb -> viewModel.userData.value?.let { openBrowser(it) }
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