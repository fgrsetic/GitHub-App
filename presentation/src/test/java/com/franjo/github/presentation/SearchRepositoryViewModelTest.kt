package com.franjo.github.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.map
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.repository.ISharedPrefs
import com.franjo.github.domain.shared.SORT_REPO_KEY
import com.franjo.github.domain.shared.SORT_STARS
import com.franjo.github.domain.usecase.GetSearchedRepositories
import com.franjo.github.presentation.features.search.SearchRepositoryViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SearchRepositoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val state: SavedStateHandle = mockk()
    private val sharedPrefs = mockk<ISharedPrefs>(relaxed = true)
    private val getSearchedRepositories: GetSearchedRepositories<Flow<PagingData<Repo>>> = mockk()
    private lateinit var viewModel: SearchRepositoryViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        viewModel =
            SearchRepositoryViewModel(
                testDispatcher,
                state,
                sharedPrefs,
                getSearchedRepositories
            )
    }


    @Test
    fun searchRepository_return_PagingData() = runBlocking {
        val mockResponse: PagingData<Repo> = mockk(relaxed = true)
        val sortBy = sharedPrefs.getValue(SORT_REPO_KEY, SORT_STARS)

        val flow = flow {
            emit(mockResponse)
        }

        coEvery {
            getSearchedRepositories.hint(Repo::class).hint(Repo::class).getSearchResultStream("User", sortBy as String)
        } coAnswers {
            flow
        }

        // Test
        val search = viewModel.searchRepository("User")

        coVerify {
            search.collect { pagingData ->
                pagingData.map {
                    it
                }
            }
        }
    }
}



