package com.franjo.github.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.shared.ResultWrapper
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.features.user.public_user.UserDetailsViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.util.UserDataPresentationMapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class UserDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: RepositoryUI = mockk()
    private val userDataPresentationMapper: UserDataPresentationMapper = mockk()
    private val getUserData: GetUserData = mockk()
    private lateinit var viewModel: UserDetailsViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        viewModel =
            UserDetailsViewModel(
                repository,
                testDispatcher,
                userDataPresentationMapper,
                getUserData
            )
    }

    @Test
    fun `successfully call for User data`() = runBlocking {
        val mockResponse: User = mockk(relaxed = true)
        coEvery {
            getUserData.invoke("User")
        } coAnswers {
            ResultWrapper.Success(mockResponse)
        }
        viewModel.getUserData("User")
        coVerify(exactly = 1) { getUserData.invoke("User") }
    }
}