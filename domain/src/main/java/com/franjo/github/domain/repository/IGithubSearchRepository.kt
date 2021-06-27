package com.franjo.github.domain.repository

interface IGithubSearchRepository<T> {
  fun getSearchResultStream(query: String, sortBy: String): T
}
