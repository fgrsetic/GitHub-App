package com.franjo.github.data.network.dto.github_repository

import com.franjo.github.domain.model.Repository
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * DataTransferObject go in this file. It is responsible for parsing responses from the server
 * or formatting objects to send to the server. We should convert these to domain objects before
 * using them.
 */

/**  This is to parse first level of network result
{
"total_count": 1981,
"incomplete_results": false,
"items": []
}
 */
@JsonClass(generateAdapter = true)
data class NetworkRepositoryContainer(
    @Json(name = "total_count")
    val totalCount: Int,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    @Json(name = "items")
    val repositoryItems: List<NetworkRepository>
)

@JsonClass(generateAdapter = true)
data class NetworkRepository(
    @Json(name = "archive_url")
    val archiveUrl: String = "",
    @Json(name = "archived")
    val archived: Boolean = false,
    @Json(name = "assignees_url")
    val assigneesUrl: String = "",
    @Json(name = "blobs_url")
    val blobsUrl: String = "",
    @Json(name = "branches_url")
    val branchesUrl: String = "",
    @Json(name = "clone_url")
    val cloneUrl: String = "",
    @Json(name = "collaborators_url")
    val collaboratorsUrl: String = "",
    @Json(name = "comments_url")
    val commentsUrl: String = "",
    @Json(name = "commits_url")
    val commitsUrl: String = "",
    @Json(name = "compare_url")
    val compareUrl: String = "",
    @Json(name = "contents_url")
    val contentsUrl: String = "",
    @Json(name = "contributors_url")
    val contributorsUrl: String = "",
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "default_branch")
    val defaultBranch: String = "",
    @Json(name = "deployments_url")
    val deploymentsUrl: String = "",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "disabled")
    val disabled: Boolean = false,
    @Json(name = "downloads_url")
    val downloadsUrl: String = "",
    @Json(name = "events_url")
    val eventsUrl: String = "",
    @Json(name = "fork")
    val fork: Boolean = false,
    @Json(name = "forks")
    val forks: Int = 0,
    @Json(name = "forks_count")
    val forksCount: Int = 0,
    @Json(name = "forks_url")
    val forksUrl: String = "",
    @Json(name = "full_name")
    val fullName: String = "",
    @Json(name = "git_commits_url")
    val gitCommitsUrl: String = "",
    @Json(name = "git_refs_url")
    val gitRefsUrl: String = "",
    @Json(name = "git_tags_url")
    val gitTagsUrl: String = "",
    @Json(name = "git_url")
    val gitUrl: String = "",
    @Json(name = "has_downloads")
    val hasDownloads: Boolean = false,
    @Json(name = "has_issues")
    val hasIssues: Boolean = false,
    @Json(name = "has_pages")
    val hasPages: Boolean = false,
    @Json(name = "has_projects")
    val hasProjects: Boolean = false,
    @Json(name = "has_wiki")
    val hasWiki: Boolean = false,
    @Json(name = "homepage")
    val homepage: String = "",
    @Json(name = "hooks_url")
    val hooksUrl: String = "",
    @Json(name = "html_url")
    val htmlUrl: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "issue_comment_url")
    val issueCommentUrl: String = "",
    @Json(name = "issue_events_url")
    val issueEventsUrl: String = "",
    @Json(name = "issues_url")
    val issuesUrl: String = "",
    @Json(name = "keys_url")
    val keysUrl: String = "",
    @Json(name = "labels_url")
    val labelsUrl: String = "",
    @Json(name = "language")
    val language: String = "",
    @Json(name = "languages_url")
    val languagesUrl: String = "",
    @Json(name = "license")
    val license: License = License(),
    @Json(name = "merges_url")
    val mergesUrl: String = "",
    @Json(name = "milestones_url")
    val milestonesUrl: String = "",
    @Json(name = "mirror_url")
    val mirrorUrl: Any? = null,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "node_id")
    val nodeId: String = "",
    @Json(name = "notifications_url")
    val notificationsUrl: String = "",
    @Json(name = "open_issues")
    val openIssues: Int = 0,
    @Json(name = "open_issues_count")
    val openIssuesCount: Int = 0,
    @Json(name = "owner")
    val owner: RepositoryOwner = RepositoryOwner(),
    @Json(name = "private")
    val `private`: Boolean = false,
    @Json(name = "pulls_url")
    val pullsUrl: String = "",
    @Json(name = "pushed_at")
    val pushedAt: String = "",
    @Json(name = "releases_url")
    val releasesUrl: String = "",
    @Json(name = "score")
    val score: Double = 0.0,
    @Json(name = "size")
    val size: Int = 0,
    @Json(name = "ssh_url")
    val sshUrl: String = "",
    @Json(name = "stargazers_count")
    val stargazersCount: Int = 0,
    @Json(name = "stargazers_url")
    val stargazersUrl: String = "",
    @Json(name = "statuses_url")
    val statusesUrl: String = "",
    @Json(name = "subscribers_url")
    val subscribersUrl: String = "",
    @Json(name = "subscription_url")
    val subscriptionUrl: String = "",
    @Json(name = "svn_url")
    val svnUrl: String = "",
    @Json(name = "tags_url")
    val tagsUrl: String = "",
    @Json(name = "teams_url")
    val teamsUrl: String = "",
    @Json(name = "trees_url")
    val treesUrl: String = "",
    @Json(name = "updated_at")
    val updatedAt: String = "",
    @Json(name = "url")
    val url: String = "",
    @Json(name = "watchers")
    val watchers: Int = 0,
    @Json(name = "watchers_count")
    val watchersCount: Int = 0
)

@JsonClass(generateAdapter = true)
data class License(
    @Json(name = "key")
    val key: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "node_id")
    val nodeId: String = "",
    @Json(name = "spdx_id")
    val spdxId: String = "",
    @Json(name = "url")
    val url: String = ""
)

// Repository name, author name, author thumbnail, watcher count, fork count, issue count, stars, programming language,
// created, lastUpdated, lastUpdated, projectHtmlUrl, ownerHtmlUrl, ownerUrl
fun NetworkRepositoryContainer.asDomainObject(): List<Repository> {
    return repositoryItems.map {
        Repository(
            name = it.name,
            author = it.owner.login,
            thumbnail = it.owner.avatarUrl,
            watchers = it.watchers,
            forks = it.forksCount,
            issuesCount = it.openIssuesCount,
            starsCount = it.stargazersCount,
            programmingLanguage = it.language,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            htmlUrl = it.htmlUrl,
            ownerHtmlUrl = it.owner.htmlUrl,
            ownerUrl = it.owner.url
        )
    }
}









