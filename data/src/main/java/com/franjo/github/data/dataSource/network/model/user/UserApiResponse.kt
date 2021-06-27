package com.franjo.github.data.dataSource.network.model.user

import com.franjo.github.domain.model.user.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserApiResponse(
  val id: Int,
  val name: String?,
  val bio: String?,
  val company: String?,
  val email: String?,
  val followers: Int?,
  val following: Int?,
  val hireable: String?,
  val location: String?,
  val url: String?,
  @Json(name = "avatar_url") val avatarUrl: String?,
  @Json(name = "html_url") val htmlUrl: String?,
  @Json(name = "public_repos") val publicRepos: Int?,
)

fun UserApiResponse.asDomainObject(): User {
  return User(
    name = name.orEmpty(),
    company = company.orEmpty(),
    location = location.orEmpty(),
    email = this.email.orEmpty(),
    hireable = this.hireable.orEmpty(),
    bio = this.bio.orEmpty(),
    publicRepos = this.publicRepos ?: 0,
    followers = this.followers ?: 0,
    following = this.following ?: 0,
    htmlUrl = this.htmlUrl.orEmpty(),
    url = this.url.orEmpty()
  )
}
