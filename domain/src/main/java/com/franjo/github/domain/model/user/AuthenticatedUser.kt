package com.franjo.github.domain.model.user

data class AuthenticatedUser(
  val id: Int,
  val name: String,
  val avatarUrl: String,
  val bio: String,
  val company: String,
  val email: String,
  val followers: Int,
  val following: Int,
  val hireable: String,
  val htmlUrl: String,
  val location: String,
  val publicRepos: Int,
  val url: String
)
