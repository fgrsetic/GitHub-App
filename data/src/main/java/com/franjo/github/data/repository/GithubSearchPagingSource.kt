package com.franjo.github.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.franjo.github.data.dataSource.network.model.repository.asDomainObject
import com.franjo.github.data.dataSource.network.service.GitHubApiService
import com.franjo.github.domain.model.repository.Repo
import com.franjo.github.domain.shared.IN_QUALIFIER
import com.franjo.github.domain.shared.STARTING_PAGE_INDEX
import okio.IOException
import retrofit2.HttpException

// Defines the source of data and how to retrieve data from that source
// It asynchronously loads the data
class GithubSearchPagingSource(
  private val apiService: GitHubApiService,
  private val query: String,
  private val sortBy: String
) : PagingSource<Int, Repo>() {

  // The LoadParams object keeps information related to the load operation
  // LoadResult can take one of the following types: LoadResult.Page, if the result was successful
  // or LoadResult.Error, in case of error
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
    // Key of the page to be loaded. If this is the first time that load is called, LoadParams.key will be null
    // The type of the paging key - in our case, the Github API uses 1-based index numbers for pages
    // STARTING_PAGE_INDEX is a initial page key
    // loadSize - the requested number of items to load
    val position = params.key ?: STARTING_PAGE_INDEX
    val apiQuery = query + IN_QUALIFIER
    return try {
      val response =
        apiService.searchRepositories(apiQuery, sortBy, position, params.loadSize)
      val repos = response.asDomainObject()
      LoadResult.Page(
        data = repos,
        prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
        nextKey = if (repos.isEmpty()) null else position + 1
      )
    } catch (exception: IOException) {
      return LoadResult.Error(exception)
    } catch (exception: HttpException) {
      return LoadResult.Error(exception)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
    TODO("Not yet implemented")
  }
}
