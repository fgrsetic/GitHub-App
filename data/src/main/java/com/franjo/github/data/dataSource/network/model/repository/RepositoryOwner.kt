package com.franjo.github.data.dataSource.network.model.repository

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// moshi-kotlin-codegen annotation processor - automatic JSON to Kotlin converter
// Generated JsonAdapter implementation at compile time,
// with @JsonClass annotation on top of class with the generateAdapter element set to true
@JsonClass(generateAdapter = true)
data class RepositoryOwner(
  val id: Int,
  val login: String?,
  val url: String?,
  @Json(name = "avatar_url") val avatarUrl: String?,
  @Json(name = "html_ur,l") val htmlUrl: String?,
  @Json(name = "organizations_url") val organizationsUrl: String?,
  @Json(name = "repos_url") val reposUrl: String?,
)
