package com.franjo.github.presentation.features.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentSearchRepositoryBinding
import com.franjo.github.presentation.features.search.SortDialogFragment.Companion.TAG
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SearchRepositoryFragment :
    BaseFragment<FragmentSearchRepositoryBinding, SearchRepositoryViewModel>() {

    override fun getFragmentView(): Int = R.layout.fragment_search_repository
    override fun getViewModel(): Class<SearchRepositoryViewModel> =
        SearchRepositoryViewModel::class.java

    private var searchResultAdapter: SearchRepositoryAdapter? = null

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY

        initSearch(query)

        navigateToDetails()

        binding.retryButton.setOnClickListener { searchResultAdapter?.retry() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }

    private fun initAdapter() {
        binding.rvSearch.adapter = searchResultAdapter?.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { searchResultAdapter!!.retry() },
            footer = ReposLoadStateAdapter { searchResultAdapter!!.retry() }
        )
        searchResultAdapter = SearchRepositoryAdapter(listener = OnItemClickListener { repository ->
            viewModel.toRepositoryDetailsNavigate(repository)
        })

        searchResultAdapter!!.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds
            binding.rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh
            binding.ivLoadingAnimation.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.rvSearch.adapter = searchResultAdapter
    }

    private fun navigateToDetails() {
        viewModel.navigateToRepositoryDetails.observe(viewLifecycleOwner, Observer { repository ->
            val action =
                SearchRepositoryFragmentDirections.actionSearchRepositoryFragmentToRepositoryDetailsFragment(
                    repository
                )
            NavHostFragment.findNavController(this).navigate(action)
            // Tell the ViewModel we've made the navigate call to prevent multiple navigation
            viewModel.onRepositoryDetailsNavigated()
        })
    }

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

//        lifecycleScope.launch {
//            @OptIn(ExperimentalPagingApi::class)
//            (searchResultAdapter?.dataRefreshFlow?.collect {
//                binding.rvSearch.scrollToPosition(0)
////                showEnterQueryMessage(true)
//            })
//        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                binding.rvSearch.scrollToPosition(0)
                search(it.toString())
                hideKeyboardFrom(requireContext(), binding.rvSearch)
            }
        }
    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepository(query).collect {
                searchResultAdapter?.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_sort -> setupConfirmationDialogButtons()
        }
        return true
    }

    private fun setupConfirmationDialogButtons() {
        val sortDialogFragment = SortDialogFragment()
        sortDialogFragment.show(childFragmentManager, TAG)
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
    }

}
