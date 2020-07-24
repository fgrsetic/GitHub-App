package com.franjo.github.presentation.util

import android.content.Context
import android.graphics.drawable.InsetDrawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun Context.generateDividerDecoration(
    insetLeft: Int = 0,
    insetTop: Int = 0,
    insetRight: Int = 0,
    insetBottom: Int = 0
): RecyclerView.ItemDecoration {
    val attrs = intArrayOf(android.R.attr.listDivider)
    val a = this.obtainStyledAttributes(attrs)
    val divider = a.getDrawable(0)

    val insetDivider = InsetDrawable(
        divider,
        insetLeft,
        insetTop,
        insetRight,
        insetBottom
    )
    a.recycle()

    val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
    decoration.setDrawable(insetDivider)
    return decoration
}

