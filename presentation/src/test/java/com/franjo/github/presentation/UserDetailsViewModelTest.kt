package com.franjo.github.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.shared.DispatcherProvider
import com.franjo.github.domain.usecase.GetUserData
import com.franjo.github.presentation.features.user.public_user.UserDetailsViewModel
import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.util.UserDataPresentationMapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class UserDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcherProvider: DispatcherProvider = mockk()
    private val repository: RepositoryUI = mockk()
    private val userDataPresentationMapper: UserDataPresentationMapper = mockk()
    private val getUserData: GetUserData = mockk()
    private lateinit var viewModel: UserDetailsViewModel

    @Before
    fun setUp() {
        clearAllMocks()
        coEvery { dispatcherProvider.provideUIContext() } returns Dispatchers.Unconfined
        viewModel =
            UserDetailsViewModel(repository, dispatcherProvider, userDataPresentationMapper, getUserData)
    }

    @Test
    fun getUserData_callUserData_returnsUser() = runBlocking {
        val mockResponse: User = mockk(relaxed = true)
        coEvery {
            getUserData.execute("User")
        } coAnswers {
            mockResponse
        }
        viewModel.getUserData("User")
        coVerify(exactly = 1) { getUserData.execute("User") }
    }
}