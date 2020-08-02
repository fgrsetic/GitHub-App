package com.franjo.github.presentation.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.ISharedPrefs
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.SORT_REPO_KEY
import com.franjo.github.domain.shared.SORT_STARS
import com.franjo.github.domain.usecase.GetAccessToken
import com.franjo.github.domain.usecase.GetLogin
import com.franjo.github.domain.usecase.GetSearchedRepositories
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.asPresentationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRepositoryViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val sharedPrefs: ISharedPrefs,
    private val getAccessToken: GetAccessToken,
    private val getLogin: GetLogin,
    private val getSearchedRepositories: GetSearchedRepositories<Flow<PagingData<Repo>>>
) : BaseViewModel(dispatcherProvider) {

    private val _navigateToRepositoryDetails = MutableLiveData<RepositoryUI>()
    val navigateToRepositoryDetails: LiveData<RepositoryUI> get() = _navigateToRepositoryDetails

    private val _navigateToUserDetails = MutableLiveData<RepositoryUI>()
    val navigateToUserDetails: LiveData<RepositoryUI> get() = _navigateToUserDetails

    private val _navigateToPrivateUserDetails = MutableLiveData<Boolean>()
    val navigateToPrivateUserDetails: LiveData<Boolean> get() = _navigateToPrivateUserDetails


    // 1) Search
    fun searchRepository(queryString: String): Flow<PagingData<RepositoryUI>> {
        val sortBy = sharedPrefs.getValue(SORT_REPO_KEY, SORT_STARS)
        return getSearchedRepositories.getSearchResultStream(queryString, sortBy as String)
            .map { pagingData ->
                pagingData.map {
                    it.asPresentationModel()
                }
                // cachedIn() method that allows us to cache the content of a Flow<PagingData> in a CoroutineScope
                // If we're doing any operations on the Flow, like map or filter,
                // we need to call cachedIn after we execute these operations to ensure we don't need to trigger them again
            }
            .cachedIn(viewModelScope)
    }

    // 2) Navigation
    fun toRepositoryDetailsNavigate(repository: RepositoryUI) {
        _navigateToRepositoryDetails.value = repository
    }

    fun onRepositoryDetailsNavigated() {
        _navigateToRepositoryDetails.value = null
    }

    fun toUserDetailsNavigate(repository: RepositoryUI) {
        _navigateToUserDetails.value = repository
    }

    fun onUserDetailsNavigated() {
        _navigateToUserDetails.value = null
    }

    fun toPrivateUserNavigate() {
        _navigateToPrivateUserDetails.value = true
    }

    fun onPrivateUserNavigated() {
        _navigateToPrivateUserDetails.value = false
    }

    // 3) Login to GitHub for authorization
    fun startLoginFlow() {
        getLogin.login()
    }

    // 4) Access token after authorization
    fun accessToken(code: String) =
        viewModelScope.launch {
            getAccessToken.execute(code)
        }

}