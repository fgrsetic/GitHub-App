package com.franjo.github.presentation

import com.franjo.github.presentation.model.RepositoryUI
import com.franjo.github.presentation.model.UserUI

interface OnItemClickListener {
    fun onItemClick(item: RepositoryUI?)
}

interface OnIconClickListener {
    fun onIconClick(item: RepositoryUI?)
}

interface OnClickListener {
    fun onClick(item: UserUI?)
}