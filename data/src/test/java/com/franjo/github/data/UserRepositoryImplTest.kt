package com.franjo.github.data

import com.franjo.github.data.dataSource.network.UserRemoteDataSource
import com.franjo.github.data.dataSource.network.dto.user.UserApiResponse
import com.franjo.github.data.repository.UserRepositoryImpl
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.shared.ResultWrapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class UserRepositoryImplTest {
  private var dataSource: UserRemoteDataSource = mockk(relaxed = true)
  private lateinit var repository: UserRepositoryImpl
  private lateinit var user: User
  private var mockResponse: UserApiResponse = mockk(relaxed = true)

  @BeforeEach
  fun setUp() {
    clearAllMocks()
    repository = UserRepositoryImpl(dataSource)
    user = mockk(relaxed = true)
  }

  @Test
  fun `return success after call to api`() =
    runBlockingTest {
      // Given behavior of mock, describes what response should be returned for which call
      coEvery {
        dataSource.fetchUserData("User")
      } coAnswers {
        ResultWrapper.Success(mockResponse)
      }
      // When
      repository.getUserData("User")
      // Then verify whether the mock was invoked as expected
      coVerify(exactly = 1) {
        repository.getUserData("User")
      }
    }
}
