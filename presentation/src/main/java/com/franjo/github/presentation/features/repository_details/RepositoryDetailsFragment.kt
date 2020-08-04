package com.franjo.github.presentation.features.repository_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentRepositoryDetailsBinding
import com.franjo.github.presentation.model.RepositoryUI
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.AndroidSupportInjection

class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_repository_details

    private lateinit var viewModel: RepositoryDetailsViewModel
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

        repository = RepositoryDetailsFragmentArgs.fromBundle(requireArguments()).repository
        val modelFactory = RepositoryDetailsViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, modelFactory).get(RepositoryDetailsViewModel::class.java)
        binding.viewModelDetails = viewModel

        bindAdapter()

        binding.ivThumbnail.setOnClickListener {
            viewModel.onUserThumbnailClicked(repository)
        }

        navigateToUserDetails()
    }

    private fun bindAdapter() {
        val tabTitles = arrayOf("About", "Releases", "Contributors")
        val repositoryInfoAdapter = RepositoryDetailsAdapter(this, tabTitles)

        binding.viewPager.adapter = repositoryInfoAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabTitles[position]
            }).attach()
    }

    private fun navigateToUserDetails() {
        viewModel.navigateToUserDetails.observe(viewLifecycleOwner, Observer { repository ->
            repository.getContentIfNotHandled()?.let {
                val action =
                    RepositoryDetailsFragmentDirections.actionRepositoryDetailsFragmentToUserDetailsFragment(
                        it
                    )
                NavHostFragment.findNavController(this).navigate(action)
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.actionWeb).isVisible = true
        menu.findItem(R.id.actionLogin).isVisible = false
        menu.findItem(R.id.actionPrivateUser).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.actionWeb -> openBrowser(repository)
        }
        return true
    }

    private fun openBrowser(repository: RepositoryUI) {
        if (Patterns.WEB_URL.matcher(repository.htmlUrl).matches()) {
            repository.htmlUrl.let { url ->
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                requireContext().startActivity(i)
            }
        }
    }
}