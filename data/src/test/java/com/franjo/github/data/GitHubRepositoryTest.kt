package com.franjo.github.data

import com.franjo.github.data.network.service.GitHubApiService
import com.franjo.github.domain.shared.DispatcherProvider
import io.mockk.clearAllMocks
import org.junit.Test
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach


internal class GitHubRepositoryTest {

    private val dispatcherProvider: DispatcherProvider = mockk()
    private val gitHubApiService: GitHubApiService = mockk()


    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `return success after api call`()  {
        // 1. Default
        // given behavior of mock, describes what response should be returned for which call
    }
}