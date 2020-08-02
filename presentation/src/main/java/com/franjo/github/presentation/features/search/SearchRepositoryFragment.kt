package com.franjo.github.presentation.features.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.OnIconClickListener
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentSearchRepositoryBinding
import com.franjo.github.presentation.features.search.SortDialogFragment.Companion.TAG
import com.franjo.github.presentation.model.RepositoryUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchRepositoryFragment : BaseFragment<FragmentSearchRepositoryBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_search_repository

    @Inject
    lateinit var viewModel: SearchRepositoryViewModel

    private var searchJob: Job? = null
    private var searchResultAdapter: SearchRepositoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        val query = binding.searchRepo.text.trim().toString()

        initAdapter()
        initSearch(query)
        navigateToRepositoryDetails()
        navigateToUserDetails()
        navigateToPrivateUser()
        // retry button should trigger a reload of the PagingData
        binding.retryButton.setOnClickListener { searchResultAdapter?.retry() }

    }

    private fun initAdapter() {
        searchResultAdapter = SearchRepositoryAdapter(
            rowListener = object : OnItemClickListener {
                override fun onItemClick(item: RepositoryUI?) {
                    item?.let { viewModel.toRepositoryDetailsNavigate(it) }
                }

            },
            iconListener = object : OnIconClickListener {
                override fun onIconClick(item: RepositoryUI?) {
                    item?.let { viewModel.toUserDetailsNavigate(it) }
                }
            })

        // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object
        // CombinedLoadStates gives us the load state for the PageSource we defined
        // CombinedLoadStates.refresh - represents the load state for loading the PagingData for the first time
        searchResultAdapter!!.addLoadStateListener { loadState ->

            if (binding.rvSearch.isVisible == loadState.source.refresh is LoadState.Loading
                && binding.progressBar.isVisible == loadState.source.refresh is LoadState.NotLoading
                && binding.retryButton.isVisible == loadState.source.refresh is LoadState.Error
            ) {
                binding.ivNoSearch.visibility = View.GONE
            }
            // Only show the list if refresh succeeds
            binding.rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner animation during initial load or refresh
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.refresh as? LoadState.Error
            errorState?.let {
                Toast.makeText(context, "Something went wrong ", Toast.LENGTH_LONG).show()
            }
        }

        binding.rvSearch.adapter = searchResultAdapter
    }


    // Search
    private fun initSearch(query: String) {
        binding.searchRepo.setText(query)

        binding.searchRepo.setOnEditorActionListener { _, actionId, _ ->
            // IME_ACTION_GO -> the action key performs a "go" operation
            // to take the user to the target of the text they typed
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                search(it.toString())
                binding.rvSearch.scrollToPosition(0)
                hideKeyboardFrom(requireContext(), binding.rvSearch)
            }
        }
    }

    // To be able to work with Flow<PagingData>, we need to launch a new coroutine
    private fun search(query: String) {
        // We want to ensure that whenever the user searches for a new query,
        // the previous query is cancelled. We hold a reference
        // to a new Job that will be cancelled every time there is a search for a new query
        searchJob?.cancel()
        // lifecycleScope is responsible for canceling the request when the activity is recreated
        searchJob = lifecycleScope.launch {
            // Collect the PagingData result
            viewModel.searchRepository(query).collectLatest {
                // Pass the PagingData to the adapter
                searchResultAdapter?.submitData(it)
            }
        }
    }


    // Navigation
    private fun navigateToRepositoryDetails() {
        viewModel.navigateToRepositoryDetails.observe(viewLifecycleOwner, Observer { repository ->
            if (repository != null) {
                val action =
                    SearchRepositoryFragmentDirections.actionSearchRepositoryFragmentToRepositoryDetailsFragment(
                        repository
                    )
                NavHostFragment.findNavController(this).navigate(action)
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.onRepositoryDetailsNavigated()
            }
        })
    }

    private fun navigateToUserDetails() {
        viewModel.navigateToUserDetails.observe(viewLifecycleOwner, Observer { repository ->
            if (repository != null) {
                val action =
                    SearchRepositoryFragmentDirections.actionSearchRepositoryFragmentToUserDetailsFragment(
                        repository
                    )
                NavHostFragment.findNavController(this).navigate(action)
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.onUserDetailsNavigated()
            }
        })
    }

    private fun navigateToPrivateUser() {
        viewModel.navigateToPrivateUserDetails.observe(
            viewLifecycleOwner,
            Observer { isPrivateUser ->
                if (isPrivateUser == true) {
                    val action =
                        SearchRepositoryFragmentDirections.actionSearchRepositoryFragmentToPrivateUserFragment()
                    NavHostFragment.findNavController(this).navigate(action)
                    // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                    viewModel.onPrivateUserNavigated()
                }
            })
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.actionSort)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSort -> setupConfirmationDialogButtons()
            R.id.actionLogin -> viewModel.startLoginFlow()
            R.id.actionPrivateUser -> viewModel.toPrivateUserNavigate()
        }
        return true
    }

    private fun setupConfirmationDialogButtons() {
        val sortDialogFragment = SortDialogFragment()
        sortDialogFragment.show(childFragmentManager, TAG)
    }

}
