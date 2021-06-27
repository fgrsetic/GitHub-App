package com.franjo.github.presentation.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.franjo.github.domain.di.MainDispatcher
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.ISharedPrefs
import com.franjo.github.domain.shared.SORT_REPO_KEY
import com.franjo.github.domain.shared.SORT_STARS
import com.franjo.github.domain.usecase.GetSearchedRepositories
import com.franjo.github.domain.usecase.IsUserLoggedIn
import com.franjo.github.domain.usecase.LogoutUser
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.asPresentationModel
import com.franjo.github.presentation.util.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  @MainDispatcher dispatcher: CoroutineDispatcher,
  private val queryState: SavedStateHandle,
  private val sharedPrefs: ISharedPrefs,
  private val getSearchedRepositories: GetSearchedRepositories<Flow<PagingData<Repo>>>,
  private val isUserLoggedIn: IsUserLoggedIn,
  private val logoutUser: LogoutUser
) : BaseViewModel(dispatcher) {

  private val _state = MutableLiveData<Event<SearchState>>()
  val state: LiveData<Event<SearchState>> get() = _state

  private var currentQueryValue: String? = null

  private var currentSearchResult: Flow<PagingData<RepositoryUI>>? = null

  private val _navigateToDetails = MutableLiveData<Event<RepositoryUI>>()
  val navigateToDetails: LiveData<Event<RepositoryUI>> get() = _navigateToDetails

  private val _navigateToUserDetails = MutableLiveData<Event<RepositoryUI>>()
  val navigateToUserDetails: LiveData<Event<RepositoryUI>> get() = _navigateToUserDetails

  private val _navigateToPrivateUserDetails = MutableLiveData<Event<Boolean>>()
  val navigateToPrivateUserDetails: LiveData<Event<Boolean>> get() = _navigateToPrivateUserDetails

  private val _navigateToAuthorization = MutableLiveData<Event<Boolean>>()
  val navigateToAuthorization: LiveData<Event<Boolean>> get() = _navigateToAuthorization

  // Save query state
  fun saveSearchQuery(query: String) {
    queryState.set(LAST_SEARCH_QUERY, query)
  }

  fun searchQuery() {
    _state.value = Event(SearchState.Search(queryState.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY))
  }

  // 1) Search
  fun searchRepository(queryString: String): Flow<PagingData<RepositoryUI>> {
    val lastResult = currentSearchResult
    if (queryString == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = queryString
    val sortBy = sharedPrefs.getValue(SORT_REPO_KEY, SORT_STARS)
    val newResult = getSearchedRepositories.getSearchResultStream(queryString, sortBy as String)
      .map { pagingData ->
        pagingData.map { repo ->
          repo.asPresentationModel()
        }
        // cachedIn() method that allows us to cache the content of a Flow<PagingData> in a CoroutineScope
        // If we're doing any operations on the Flow, like map or filter,
        // we need to call cachedIn after we execute these operations to ensure we don't need to trigger them again
      }
      .cachedIn(viewModelScope)
    currentSearchResult = newResult
    return newResult
  }

  // 2) Navigation
  fun onItemRowClick(repository: RepositoryUI) {
    // Trigger the event by setting a new Event as a new value
    _navigateToDetails.value = Event(repository)
  }

  fun onMenuItemUserClicked() {
    _navigateToPrivateUserDetails.value = Event(true)
  }

  fun onMenuItemAuthorizeClicked() {
    _navigateToAuthorization.value = Event(true)
  }

  fun onItemRowUserIconClicked(repository: RepositoryUI) {
    _navigateToUserDetails.value = Event(repository)
  }

  // 3) Login/Logout
  fun checkUserLoggedIn() {
    _state.value = Event(SearchState.User(isUserLoggedIn()))
  }

  fun logout() {
    logoutUser
    _state.value = Event(SearchState.User(isUserLoggedIn()))
  }

  companion object {
    const val LAST_SEARCH_QUERY: String = "last_search_query"
    const val DEFAULT_QUERY = "Android"
  }
}
