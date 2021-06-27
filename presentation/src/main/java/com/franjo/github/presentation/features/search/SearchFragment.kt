package com.franjo.github.presentation.features.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.franjo.github.domain.shared.ErrorModel
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.OnIconClickListener
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentSearchBinding
import com.franjo.github.presentation.features.authorization.LoginState
import com.franjo.github.presentation.features.search.SortDialogFragment.Companion.TAG
import com.franjo.github.presentation.model.RepositoryUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

  override fun getFragmentView(): Int = R.layout.fragment_search

  @Inject
  lateinit var viewModel: SearchViewModel

  private var searchJob: Job? = null
  private var searchAdapter: SearchAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.viewModel = viewModel

    viewModel.searchQuery()

    viewModel.state.observe(viewLifecycleOwner, {
      action(it.getContentIfNotHandled())
    })
    // Retry button should trigger a reload of the PagingData
    binding.retryButton.setOnClickListener { searchAdapter?.retry() }
  }

  private fun action(state: SearchState?) {
    when (state) {
      is SearchState.HideKeyboard -> hideKeyboardFrom(requireContext(), binding.rvSearch)
      is SearchState.Init -> {
        initAdapter()
        loadStateListener()
        listenEditTextQuery()
      }
      is SearchState.Search -> {
        search(state.query)
        initSearch(state.query)
      }
      is SearchState.User -> requireActivity().invalidateOptionsMenu()
      is SearchState.Error -> state.error.message(requireContext())
      is SearchState.NavigateToDetails -> navigateToDetails()
      is SearchState.NavigateToUserDetails -> navigateToUserDetails()
      is SearchState.NavigateToPrivateUserDetails -> navigateToPrivateUser()
      is SearchState.NavigateToAuthorization -> navigateToAuthorization()
    }
  }

  private fun initAdapter() {
    searchAdapter = SearchAdapter(
      rowListener = object : OnItemClickListener {
        override fun onItemClick(item: RepositoryUI?) {
          item?.let {
            viewModel.onItemRowClick(it)
          }
        }
      },
      iconListener = object : OnIconClickListener {
        override fun onIconClick(item: RepositoryUI?) {
          item?.let { viewModel.onItemRowUserIconClicked(it) }
        }
      }
    )
    val linearLayoutManager = LinearLayoutManager(requireContext())
    val dividerItemDecoration =
      DividerItemDecoration(requireContext(), linearLayoutManager.orientation)

    binding.rvSearch.also {
//      it.adapter = searchResultAdapter
//      it.addItemDecoration(dividerItemDecoration)
    }
    //      binding.rvSearch.adapter = searchResultAdapter
  }

  private fun loadStateListener() {
    // This callback notifies us every time there's a change in the load state via a CombinedLoadStates object
    // CombinedLoadStates gives us the load state for the PageSource we defined
    // CombinedLoadStates.refresh - represents the load state for loading the PagingData for the first time
    searchAdapter!!.addLoadStateListener { loadState ->
      // Only show the list if refresh succeeds
      // binding.rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
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
        searchAdapter?.submitData(it)
      }
    }
  }

  private fun listenEditTextQuery() {
    binding.searchRepo.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable) {
        viewModel.saveSearchQuery(s.trim().toString())
      }
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
  }

  private fun hideKeyboardFrom(context: Context, view: View) {
    val imm: InputMethodManager =
      context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

  override fun onResume() {
    super.onResume()
    viewModel.checkUserLoggedIn()
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    menu.findItem(R.id.actionSort)?.isVisible = true
    val lastState = viewModel.state.value
    if (lastState?.getContentIfNotHandled() is SearchState.User) {
      inflater.inflate(R.menu.menu_main, menu)
      menu.findItem(R.id.actionLogin)?.isVisible = !(lastState.getContentIfNotHandled() as SearchState.User).userLoggedIn
      menu.findItem(R.id.actionLogout)?.isVisible = (lastState.getContentIfNotHandled() as SearchState.User).userLoggedIn
      menu.findItem(R.id.actionPrivateUser)?.isVisible = !(lastState.getContentIfNotHandled() as SearchState.User).userLoggedIn
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.actionSort -> setupConfirmationDialogButtons()
      R.id.actionLogin -> viewModel.onMenuItemAuthorizeClicked()
      R.id.actionLogout -> viewModel.logout()
      R.id.actionPrivateUser -> viewModel.onMenuItemUserClicked()
    }
    return true
  }

  private fun setupConfirmationDialogButtons() {
    val sortDialogFragment = SortDialogFragment()
    sortDialogFragment.show(childFragmentManager, TAG)
  }

  // Navigation
  private fun navigateToDetails() {
    viewModel.navigateToDetails.observe(
      viewLifecycleOwner,
      { repository ->
        repository.getContentIfNotHandled()?.let {
          val action =
            SearchFragmentDirections.actionSearchFragmentToRepositoryDetailsFragment(
              it
            )
          NavHostFragment.findNavController(this).navigate(action)
        }
      }
    )
  }

  private fun navigateToUserDetails() {
    viewModel.navigateToUserDetails.observe(
      viewLifecycleOwner,
      { repository ->
        repository.getContentIfNotHandled()?.let {
          val action =
            SearchFragmentDirections.actionSearchFragmentToUserDetailsFragment(
              it
            )
          NavHostFragment.findNavController(this).navigate(action)
        }
      }
    )
  }

  private fun navigateToPrivateUser() {
    viewModel.navigateToPrivateUserDetails.observe(
      viewLifecycleOwner,
      { isPrivateUser ->
        isPrivateUser.getContentIfNotHandled()?.let {
          val action =
            SearchFragmentDirections.actionSearchFragmentToPrivateUserFragment()
          NavHostFragment.findNavController(this).navigate(action)
        }
      }
    )
  }

  private fun navigateToAuthorization() {
    viewModel.navigateToAuthorization.observe(viewLifecycleOwner,{ shouldAuthorize ->
      shouldAuthorize.getContentIfNotHandled()?.let {
        val action =  SearchFragmentDirections.actionSearchFragmentToAuthorizationFragment()
        NavHostFragment.findNavController(this).navigate(action)
      }
    })
  }
}

private fun ErrorModel.message(context: Context): String = when(this) {
  ErrorModel.NetworkError -> context.getString(R.string.network_error_message)
  ErrorModel.NotFound -> context.getString(R.string.not_found_error_message)
  ErrorModel.Forbidden -> context.getString(R.string.forbidden_error_message)
  ErrorModel.Unavailable -> context.getString(R.string.unavailable_error_message)
  ErrorModel.Unknown -> context.getString(R.string.unknown_error_message)
  is ErrorModel.GenericError<*> -> { context.getString(R.string.unknown_error_message)
  }
}
