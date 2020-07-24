package com.franjo.github.presentation.model

import android.os.Parcelable
import com.franjo.github.domain.model.Repository
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryUI(
    val name: String,
    val author: String,
    val thumbnail: String,
    val watcherCount: Int,
    val forkCount: Int,
    val issuesCount: Int,
    val starsCount: Int,
    val programmingLanguage: String,
    val createdAt: String,
    val updatedAt: String,
    val htmlUrl: String,
    val ownerHtmlUrl: String,
    val ownerUrl: String
) : Parcelable


fun List<Repository>.asPresentationModel(): List<RepositoryUI> {
    return map {
        RepositoryUI(
            name = it.name,
            author = it.author,
            thumbnail = it.thumbnail,
            watcherCount = it.watcherCount,
            forkCount = it.forksCount,
            issuesCount = it.issuesCount,
            starsCount = it.starsCount,
            programmingLanguage = it.programmingLanguage,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            htmlUrl = it.htmlUrl,
            ownerHtmlUrl = it.ownerHtmlUrl,
            ownerUrl = it.ownerUrl
        )
    }
}
