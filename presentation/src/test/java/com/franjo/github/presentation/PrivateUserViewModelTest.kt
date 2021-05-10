package com.franjo.github.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.usecase.GetAuthenticatedUser
import com.franjo.github.presentation.features.user.private_user.PrivateUserViewModel
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
internal class PrivateUserViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val userDataPresentationMapper: UserDataPresentationMapper = mockk()
    private val getAuthenticatedUser: GetAuthenticatedUser = mockk()
    private lateinit var viewModel: PrivateUserViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        viewModel =
            PrivateUserViewModel(testDispatcher, getAuthenticatedUser, userDataPresentationMapper)
    }

    @Test
    fun loadPrivateUser_callAuthenticatedUser_returnsAuthenticatedUser() = runBlocking {
        val mockResponse: AuthenticatedUser = mockk(relaxed = true)
        coEvery {
            getAuthenticatedUser.execute()
        } coAnswers {
            mockResponse
        }
        viewModel.loadPrivateUser()
        coVerify(exactly = 1) { getAuthenticatedUser.execute() }
    }
}