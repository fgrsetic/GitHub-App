package com.franjo.github.domain.model.repository

data class Repo(
  val id: Int,
  val author: String,
  val description: String,
  val forksCount: Int,
  val fullName: String,
  val programmingLanguage: String,
  val name: String,
  val openIssues: Int,
  val issuesCount: Int,
  val starsCount: Int,
  val thumbnail: String,
  val url: String,
  val watchers: Int,
  val watcherCount: Int,
  val updatedAt: String,
  val createdAt: String,
  val htmlUrl: String
)
