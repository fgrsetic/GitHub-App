package com.franjo.github.presentation.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.franjo.github.domain.model.Repo
import com.franjo.github.domain.service.ISharedPrefsService
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.SORT_REPO_KEY
import com.franjo.github.domain.shared.SORT_STARS
import com.franjo.github.domain.usecase.GetSearchedRepositories
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.asPresentationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

enum class GithubApiStatus { LOADING, ERROR, DONE }

class SearchRepositoryViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val sharedPrefsService: ISharedPrefsService,
    private val getSearchedRepositories: GetSearchedRepositories<Flow<PagingData<Repo>>>
) : BaseViewModel(dispatcherProvider) {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<RepositoryUI>>? = null

    private val _navigateToRepositoryDetails by lazy { MutableLiveData<RepositoryUI>() }
    val navigateToRepositoryDetails: LiveData<RepositoryUI> get() = _navigateToRepositoryDetails

    private val _status = MutableLiveData<GithubApiStatus>()
    val status: LiveData<GithubApiStatus> get() = _status


    fun searchRepository(queryString: String): Flow<PagingData<RepositoryUI>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val sortBy = sharedPrefsService.getValue(SORT_REPO_KEY, SORT_STARS)
        val newResult: Flow<PagingData<RepositoryUI>> =
            getSearchedRepositories.invoke(queryString, sortBy as String)
                .map { pagingData ->
                    pagingData.map { it.asPresentationModel() }
                    // cachedIn() method that allows us to cache the content of a Flow<PagingData> in a CoroutineScope
                    // If we're doing any operations on the Flow, like map or filter,
                    // we need to call cachedIn after we execute these operations to ensure we don't need to trigger them again
                }.cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    // Navigation
    fun toRepositoryDetailsNavigate(repository: RepositoryUI) {
        _navigateToRepositoryDetails.value = repository
    }

    fun onRepositoryDetailsNavigated() {
        _navigateToRepositoryDetails.value = null
    }
}