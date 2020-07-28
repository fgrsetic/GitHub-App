package com.franjo.github.presentation.features.repository_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.OnIconClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentRepositoryDetailsBinding
import com.franjo.github.presentation.features.search.SearchRepositoryFragmentDirections
import com.franjo.github.presentation.model.RepositoryUI
import com.google.android.material.tabs.TabLayoutMediator

class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_repository_details

    private lateinit var viewModel: RepositoryDetailsViewModel
    private lateinit var repository: RepositoryUI

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repository = RepositoryDetailsFragmentArgs.fromBundle(requireArguments()).repository
        val modelFactory = RepositoryDetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, modelFactory).get(RepositoryDetailsViewModel::class.java)
        binding.viewModelDetails = viewModel

        bindAdapter()
        setHasOptionsMenu(true)

        binding.ivThumbnail.setOnClickListener {
            viewModel.toUserDetailsNavigate(repository)
        }

        observeUserDetails()
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

    private fun observeUserDetails() {
        viewModel.navigateToUserDetails.observe(viewLifecycleOwner, Observer { repository ->
            if (repository != null) {
                val action =
                    RepositoryDetailsFragmentDirections.actionRepositoryDetailsFragmentToUserDetailsFragment(
                        repository
                    )
                NavHostFragment.findNavController(this).navigate(action)
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.onUserDetailsNavigated()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.details_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_go_to_web -> openBrowser(repository)
        }
        return true
    }

    private fun openBrowser(repository: RepositoryUI) {
        if (Patterns.WEB_URL.matcher(repository.htmlUrl).matches()) {
            repository.htmlUrl.let {url ->
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                requireContext().startActivity(i)
            }
        }
    }

}