package com.franjo.github.presentation.model

import android.os.Parcelable
import com.franjo.github.domain.model.repository.Repo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryUI(
    val id: Int,
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


    fun Repo.asPresentationModel(): RepositoryUI {
        return RepositoryUI(
            id = id,
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


