package com.franjo.github.data.dataSource.network.model.repository

import com.franjo.github.domain.model.repository.Repo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**  This is to parse first level of network result
{
"total_count": 1981,
"incomplete_results": false,
"items": []
}
 */
@JsonClass(generateAdapter = true)
data class RepositoryApiResponse(
  @Json(name = "total_count") val totalCount: Int? = null,
  @Json(name = "incomplete_results") val incompleteResults: Boolean,
  @Json(name = "items") val repositoryItems: List<RepositoryItem> = emptyList()
)

// Everything is nullable, since API responses can return no fields
@JsonClass(generateAdapter = true)
data class RepositoryItem(
  val id: Int,
  val name: String?,
  val language: String?,
  val description: String?,
  val owner: RepositoryOwner?,
  val url: String?,
  val watchers: Int?,
  val createdAt: String?,
  val updatedAt: String?,
  @Json(name = "full_name") val fullName: String?,
  @Json(name = "forks_count") val forksCount: Int?,
  @Json(name = "open_issues") val openIssues: Int?,
  @Json(name = "open_issues_count") val openIssuesCount: Int?,
  @Json(name = "stargazers_count") val stargazersCount: Int?,
  @Json(name = "watchers_count") val watchersCount: Int?,
  @Json(name = "html_url") val htmlUrl: String?
)

fun RepositoryApiResponse.asDomainObject(): List<Repo> {
  return repositoryItems.map {
    Repo(
      id = it.id,
      name = it.name.orEmpty(),
      fullName = it.fullName.orEmpty(),
      author = it.owner?.login.orEmpty(),
      thumbnail = it.owner?.avatarUrl.orEmpty(),
      description = it.description.orEmpty(),
      watcherCount = it.watchersCount ?: 0,
      forksCount = it.forksCount ?: 0,
      issuesCount = it.openIssuesCount ?: 0,
      starsCount = it.stargazersCount ?: 0,
      programmingLanguage = it.language.orEmpty(),
      watchers = it.watchers ?: 0,
      openIssues = it.openIssues ?: 0,
      url = it.url.orEmpty(),
      createdAt = it.createdAt.orEmpty(),
      updatedAt = it.updatedAt.orEmpty(),
      htmlUrl = it.htmlUrl.orEmpty()
    )
  }
}
