package com.franjo.github.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.map
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.ISharedPrefs
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.shared.SORT_REPO_KEY
import com.franjo.github.domain.shared.SORT_STARS
import com.franjo.github.domain.usecase.GetAccessToken
import com.franjo.github.domain.usecase.GetLogin
import com.franjo.github.domain.usecase.GetSearchedRepositories
import com.franjo.github.presentation.features.search.SearchRepositoryViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SearchRepositoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcherProvider: DispatcherProvider = mockk()
    private val state: SavedStateHandle = mockk()
    private val sharedPrefs = mockk<ISharedPrefs>(relaxed = true)
    private val getAccessToken: GetAccessToken = mockk()
    private val getLogin: GetLogin = mockk()
    private val getSearchedRepositories: GetSearchedRepositories<Flow<PagingData<Repo>>> = mockk()
    private lateinit var viewModel: SearchRepositoryViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        coEvery { dispatcherProvider.provideUIContext() } returns Dispatchers.Unconfined
        viewModel =
            SearchRepositoryViewModel(
                dispatcherProvider,
                state,
                sharedPrefs,
                getAccessToken,
                getLogin,
                getSearchedRepositories
            )
    }


    @Test
    fun searchRepository_return_PagingData() = runBlocking {
//        val mockResponse: PagingData<Repo> = mockk(relaxed = true)
//        val sortBy = sharedPrefs.getValue(SORT_REPO_KEY, SORT_STARS)
//        coEvery {
//            getSearchedRepositories.getSearchResultStream("User", sortBy as String)
//        } coAnswers {
//            mockResponse
//        }
//        getSearchedRepositories.getSearchResultStream("User", sortBy as String)
//        // Test
//        val flow = viewModel.searchRepository("User")
//
//        coVerify {
//            getSearchedRepositories.getSearchResultStream("User", sortBy)
//        }
    }
}



