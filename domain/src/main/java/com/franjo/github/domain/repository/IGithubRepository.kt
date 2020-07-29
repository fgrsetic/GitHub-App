package com.franjo.github.domain.repository

interface IGithubRepository<T> {

    fun getSearchResultStream(query: String, sortBy: String): T
}