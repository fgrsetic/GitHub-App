package com.franjo.github.presentation.model

import android.os.Parcelable
import com.franjo.github.domain.model.user.AuthenticatedUser
import com.franjo.github.domain.model.user.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUI(
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val email: String,
    val hireable: String,
    val bio: String,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    val htmlUrl: String,
    val url: String
) : Parcelable

fun User.asPresentationModel(): UserUI {
    return UserUI(
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        hireable = hireable,
        bio = bio,
        publicRepos = publicRepos,
        publicGists = publicGists,
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
        blog = blog,
        location = location,
        email = email,
        hireable = hireable,
        bio = bio,
        publicRepos = publicRepos,
        publicGists = publicGists,
        followers = followers,
        following = following,
        htmlUrl = htmlUrl,
        url = url
    )
}