package com.franjo.github.domain.model

data class SearchRequest(
    val query: String,
    val sort: String,
    val page: Int = 1
)