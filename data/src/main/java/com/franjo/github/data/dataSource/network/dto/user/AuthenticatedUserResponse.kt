package com.franjo.github.data.dataSource.network.dto.user

import com.franjo.github.domain.model.user.AuthenticatedUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticatedUserResponse(
  @Json(name = "avatar_url")
  val avatarUrl: String,
  @Json(name = "bio")
  val bio: String?,
  @Json(name = "blog")
  val blog: String,
  @Json(name = "company")
  val company: String?,
  @Json(name = "created_at")
  val createdAt: String,
  @Json(name = "email")
  val email: String?,
  @Json(name = "events_url")
  val eventsUrl: String,
  @Json(name = "followers")
  val followers: Int,
  @Json(name = "followers_url")
  val followersUrl: String,
  @Json(name = "following")
  val following: Int,
  @Json(name = "following_url")
  val followingUrl: String? = "",
  @Json(name = "gists_url")
  val gistsUrl: String? = "",
  @Json(name = "gravatar_id")
  val gravatarId: String? = "",
  @Json(name = "hireable")
  val hireable: String? = "",
  @Json(name = "html_url")
  val htmlUrl: String? = "",
  @Json(name = "id")
  val id: Int = 0,
  @Json(name = "location")
  val location: String? = null,
  @Json(name = "login")
  val login: String? = "",
  @Json(name = "name")
  val name: String? = "",
  @Json(name = "node_id")
  val nodeId: String? = "",
  @Json(name = "organizations_url")
  val organizationsUrl: String? = "",
  @Json(name = "public_gists")
  val publicGists: Int = 0,
  @Json(name = "public_repos")
  val publicRepos: Int = 0,
  @Json(name = "received_events_url")
  val receivedEventsUrl: String? = "",
  @Json(name = "repos_url")
  val reposUrl: String? = "",
  @Json(name = "site_admin")
  val siteAdmin: Boolean = false,
  @Json(name = "starred_url")
  val starredUrl: String? = "",
  @Json(name = "subscriptions_url")
  val subscriptionsUrl: String? = "",
  @Json(name = "twitter_username")
  val twitterUsername: String? = "",
  @Json(name = "type")
  val type: String? = "",
  @Json(name = "updated_at")
  val updatedAt: String? = "",
  @Json(name = "url")
  val url: String? = ""
)

fun AuthenticatedUserResponse.asDomainObject(): AuthenticatedUser {
  return AuthenticatedUser(
    avatarUrl = this.avatarUrl,
    bio = this.bio.orEmpty(),
    blog = this.blog,
    company = this.company.orEmpty(),
    createdAt = this.createdAt,
    email = this.email.orEmpty(),
    eventsUrl = this.eventsUrl,
    following = this.following,
    followers = this.followers,
    followersUrl = this.followersUrl,
    followingUrl = this.followingUrl.orEmpty(),
    gistsUrl = this.gistsUrl.orEmpty(),
    gravatarId = this.gravatarId.orEmpty(),
    hireable = this.hireable.orEmpty(),
    htmlUrl = this.htmlUrl.orEmpty(),
    id = this.id,
    location = this.location.orEmpty(),
    login = this.login.orEmpty(),
    name = this.name.orEmpty(),
    nodeId = this.nodeId.orEmpty(),
    organizationsUrl = this.organizationsUrl.orEmpty(),
    publicGists = this.publicGists,
    publicRepos = this.publicRepos,
    receivedEventsUrl = this.receivedEventsUrl.orEmpty(),
    reposUrl = this.reposUrl.orEmpty(),
    siteAdmin = this.siteAdmin,
    starredUrl = this.starredUrl.orEmpty(),
    subscriptionsUrl = this.subscriptionsUrl.orEmpty(),
    type = this.type.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    url = this.url.orEmpty()
  )
}
