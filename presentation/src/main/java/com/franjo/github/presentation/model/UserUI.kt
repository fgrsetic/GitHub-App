package com.franjo.github.presentation.model

import android.os.Parcelable
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.model.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUI(
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
) : Parcelable

fun User.asPresentationModel(): UserUI {
  return UserUI(
    name = name,
    company = company,
    location = location,
    email = email,
    hireable = hireable,
    bio = bio,
    publicRepos = publicRepos,
    followers = followers,
    following = following,
    htmlUrl = htmlUrl,
    url = url
  )
}

fun AuthenticatedUser.asPresentationModel(): UserUI {
  return UserUI(
    name = name,
    company = company,
    location = location,
    email = email,
    hireable = hireable,
    bio = bio,
    publicRepos = publicRepos,
    followers = followers,
    following = following,
    htmlUrl = htmlUrl,
    url = url
  )
}
