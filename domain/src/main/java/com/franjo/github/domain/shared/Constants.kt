package com.franjo.github.domain.shared

// q = SEARCH_KEYWORD_1 + SEARCH_KEYWORD_N + QUALIFIER_1 + QUALIFIER_N
// Search for all repositories owned by defunkt that contained the word GitHub and Octocat in the README file
// you would use the following query "q=GitHub+Octocat+in:readme+user:defunkt"
// with the search repositories endpoint

// Search repositories
// name or description contains a specific word
const val IN_QUALIFIER = "in:name,description"
const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 10

// Sorting
const val SORT_REPO_KEY = "sort_repo_key"
const val SORT_STARS = "stars"
const val SORT_FORKS = "forks"
const val SORT_UPDATES = "updated"

// Autorization
const val SCOPE = "repo" // -> or user
const val CODE_PARAMETER = "code"
const val REDIRECT_URI_CALLBACK = "searchgithubrepo://callback"

// Encrypted shared prefs
const val ACCESS_TOKEN_KEY = "access_token"
const val SECRET_SHARED_PREFS = "secret_shared_prefs"
