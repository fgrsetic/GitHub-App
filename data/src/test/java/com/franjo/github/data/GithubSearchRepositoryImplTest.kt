package com.franjo.github.data

import com.franjo.github.data.network.dto.github_user.UserApiResponse
import com.franjo.github.data.network.dto.github_user.asDomainObject
import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.data.repository.GithubSearchRepositoryImpl
import com.franjo.github.data.repository.UserRepositoryImpl
import com.franjo.github.domain.shared.DispatcherProvider
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class GithubSearchRepositoryImplTest {

    private val dispatcherProvider: DispatcherProvider = mockk()
    private var gitHubApiService: GitHubApiService = mockk(relaxed = true)
    private lateinit var repository: UserRepositoryImpl
    private lateinit var mockResponse: UserApiResponse

    @BeforeEach
    fun setUp() {
        clearAllMocks()
        repository = UserRepositoryImpl(dispatcherProvider, gitHubApiService)
        mockResponse = mockk(relaxed = true)
    }

    @Test
    fun `return success after call to api`() =
        runBlocking {
            coEvery { dispatcherProvider.provideIOContext() } returns Dispatchers.Unconfined
            // Given behavior of mock, describes what response should be returned for which call
            coEvery {
                gitHubApiService.getUserDataAsync(any()).await()
            } coAnswers {
                mockResponse
            }
            // When
            val actualResult = repository.getUserData("User")
            // Then verify whether the mock was invoked as expected
            coVerify { gitHubApiService.getUserDataAsync(any()).await() }
            val expectedResult = mockResponse.asDomainObject()
            assert(expectedResult == actualResult)
        }

}

