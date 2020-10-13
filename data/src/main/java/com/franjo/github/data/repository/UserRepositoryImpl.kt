package com.franjo.github.data.repository

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import com.franjo.github.data.dataSource.UserRemoteDataSource
import com.franjo.github.data.network.dto.githubUser.asDomainObject
import com.franjo.github.domain.model.user.User
import com.franjo.github.domain.repository.IUserRepository
import com.franjo.github.domain.shared.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserRemoteDataSource
) : IUserRepository {

    override suspend fun getUserData(query: String): ResultWrapper<User?> {
        return store.get(query)
    }

    private val store: Store<String, ResultWrapper<User?>> = StoreBuilder
        .from(
            Fetcher.of { userName: String ->
                dataSource.fetchUserData(userName)
                    .map { userApiResponse ->
                        userApiResponse?.asDomainObject()
                    }
            }
        )
        .build()

}