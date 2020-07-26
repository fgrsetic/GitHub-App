package com.franjo.github.presentation.features.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.ExperimentalPagingApi
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentSearchRepositoryBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchRepositoryFragment :
    BaseFragment<FragmentSearchRepositoryBinding, SearchRepositoryViewModel>() {

    override fun getFragmentView(): Int = R.layout.fragment_search_repository
    override fun getViewModel(): Class<SearchRepositoryViewModel> =
        SearchRepositoryViewModel::class.java

    private var searchResultAdapter: SearchRepositoryAdapter? = null

    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepository(query).collectLatest {
                searchResultAdapter?.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        initAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)

        navigateToDetails()
        navigateToSortDialogFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }

    private fun initAdapter() {
        searchResultAdapter = SearchRepositoryAdapter(listener = OnItemClickListener { repository ->
            viewModel.toRepositoryDetailsNavigate(repository)
        })
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

        lifecycleScope.launch {
            @OptIn(ExperimentalPagingApi::class)
            (searchResultAdapter?.dataRefreshFlow?.collect {
                binding.rvSearch.scrollToPosition(0)
            })
        }
    }

    private fun updateRepoListFromInput() {
        binding.searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                binding.rvSearch.scrollToPosition(0)
                search(it.toString())
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.rvSearch.visibility = View.VISIBLE
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
            R.id.action_sort -> openSortDialog()
        }
        return true
    }

    private fun openSortDialog() {
        viewModel.showSortDialogFragment()
    }

    private fun navigateToSortDialogFragment() {
        viewModel.navigateToSortDialogFragment.observe(viewLifecycleOwner, Observer {it ->
            val action = SearchRepositoryFragmentDirections.actionSearchRepositoryFragmentToSortDialogFragment()
            NavHostFragment.findNavController(this).navigate(action)
            viewModel.showSortFragmentComplete()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        searchJob?.cancel()
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
    }

}
