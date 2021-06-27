package com.franjo.github.data.dataSource.network.model.user

import com.franjo.github.domain.model.user.AuthenticatedUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticatedUserResponse(
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
  @Json(name = "avatar_url") val avatarUrl: String,
  @Json(name = "public_repos") val publicRepos: Int,
  @Json(name = "html_url") val htmlUrl: String?,
)

fun AuthenticatedUserResponse.asDomainObject(): AuthenticatedUser {
  return AuthenticatedUser(
    id = this.id,
    name = this.name.orEmpty(),
    avatarUrl = this.avatarUrl,
    bio = this.bio.orEmpty(),
    company = this.company.orEmpty(),
    email = this.email.orEmpty(),
    following = this.following ?: 0,
    followers = this.followers ?: 0,
    hireable = this.hireable.orEmpty(),
    htmlUrl = this.htmlUrl.orEmpty(),
    location = this.location.orEmpty(),
    publicRepos = this.publicRepos,
    url = this.url.orEmpty()
  )
}
