package com.franjo.github.presentation.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franjo.github.domain.model.SearchRequest
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetSearchedRepositories
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.franjo.github.domain.shared.Result
import com.franjo.github.presentation.BaseViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.asPresentationModel

enum class GithubApiStatus { LOADING, ERROR, DONE }

class SearchRepositoryViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val getSearchedRepositories: GetSearchedRepositories
) : BaseViewModel(dispatcherProvider) {

    private val _status = MutableLiveData<GithubApiStatus>()
    val status: LiveData<GithubApiStatus> get() = _status

    private val _searchedRepositoryList by lazy { MutableLiveData<List<RepositoryUI>>() }
    val searchedRepositoryList: LiveData<List<RepositoryUI>> get() = _searchedRepositoryList

    private val _navigateToRepositoryDetails by lazy { MutableLiveData<RepositoryUI>() }
    val navigateToRepositoryDetails: LiveData<RepositoryUI> get() = _navigateToRepositoryDetails

    init {
        fetchRepositoryList("test")
    }

    private fun fetchRepositoryList(query: String) {
        viewModelScope.launch {

            val request = SearchRequest(
                query = query,
                sort = "stars"
            )
            _status.value = GithubApiStatus.LOADING
            when (val result = getSearchedRepositories.execute(request)) {
                is Result.Success -> {
                    _status.value = GithubApiStatus.DONE
                    _searchedRepositoryList.postValue(result.value.asPresentationModel())
                }
                is Result.Error -> {
                    _status.value = GithubApiStatus.ERROR
                }

            }

        }
    }

    // Navigation
    fun toRepositoryDetailsNavigate(repository: RepositoryUI) {
        _navigateToRepositoryDetails.value = repository
    }

    fun onRepositoryDetailsNavigated() {
        _navigateToRepositoryDetails.value = null
    }

}