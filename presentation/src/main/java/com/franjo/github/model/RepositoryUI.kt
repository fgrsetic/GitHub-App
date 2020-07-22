package com.franjo.github.model

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


fun Repository.asPresentationModel(): RepositoryUI {
    return RepositoryUI(
        name = name,
        author = author,
        thumbnail = thumbnail,
        watcherCount = watcherCount,
        forkCount = forksCount,
        issuesCount = issuesCount,
        starsCount = starsCount,
        programmingLanguage = programmingLanguage,
        createdAt = createdAt,
        updatedAt = updatedAt,
        htmlUrl = htmlUrl,
        ownerHtmlUrl = ownerHtmlUrl,
        ownerUrl = ownerUrl
    )
}
