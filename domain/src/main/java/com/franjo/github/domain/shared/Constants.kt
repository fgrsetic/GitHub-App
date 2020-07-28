package com.franjo.github.domain.shared

// q = SEARCH_KEYWORD_1 + SEARCH_KEYWORD_N + QUALIFIER_1 + QUALIFIER_N
// Search for all repositories owned by defunkt that contained the word GitHub and Octocat in the README file
// you would use the following query "q=GitHub+Octocat+in:readme+user:defunkt"
// with the search repositories endpoint

// name or description contains a specific word
const val IN_QUALIFIER = "in:name,description"
const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 50

// Sorting
const val SORT_REPO_KEY = "sort_repo_key"
const val SORT_STARS = "stars"
const val SORT_FORKS = "forks"
const val SORT_UPDATES = "updated"
