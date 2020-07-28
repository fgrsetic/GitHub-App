package com.franjo.github.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.franjo.github.presentation.R
import com.franjo.github.presentation.features.search.GithubApiStatus


// Binding adapter used to display images from URL using Glide
@BindingAdapter("thumbnailPath")
fun setRepositoryOwnerThumbnailUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imageView)
}

// Binding adapter to show the GithubApi status in the ImageView and show/hide the view
@BindingAdapter("imageVisibility")
fun bindStatus(statusImageView: ImageView, status: GithubApiStatus?) {
    when (status) {
        GithubApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        GithubApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        GithubApiStatus.DONE ->
            statusImageView.visibility = View.GONE
    }
}





