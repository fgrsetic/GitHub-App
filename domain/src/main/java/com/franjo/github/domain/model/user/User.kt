package com.franjo.github.domain.model.user

data class User(
  val name: String,
  val company: String,
  val location: String,
  val email: String,
  val hireable: String,
  val bio: String,
  val publicRepos: Int,
  val followers: Int,
  val following: Int,
  val htmlUrl: String,
  val url: String
)
