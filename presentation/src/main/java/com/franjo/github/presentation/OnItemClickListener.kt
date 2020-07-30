package com.franjo.github.presentation

import com.franjo.github.presentation.model.RepositoryUI

interface OnItemClickListener {
    fun onItemClick(item: RepositoryUI?)
}

interface OnIconClickListener {
    fun onIconClick(item: RepositoryUI?)
}

