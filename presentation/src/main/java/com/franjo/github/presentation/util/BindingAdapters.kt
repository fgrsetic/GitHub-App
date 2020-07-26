package com.franjo.github.presentation.util

import android.opengl.Visibility
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.franjo.github.presentation.R
import com.franjo.github.presentation.features.search.GithubApiStatus
import com.franjo.github.presentation.features.search.SearchRepositoryAdapter
import com.franjo.github.presentation.model.RepositoryUI


// Binding adapter used to display images from URL using Glide
@BindingAdapter("thumbnailPath")
fun setRepositoryOwnerThumbnailUrl(imageView: ImageView, url: String?) {
//    val posterPath = buildPosterUrl(url)
//    val imgUri = posterPath?.toUri()?.buildUpon()?.scheme("https")?.build()
    Glide.with(imageView.context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.icon_broken_image)
        )
        .into(imageView)
}

// Binding adapter to show the GithubApi status in the ImageView and show/hide the view
@BindingAdapter("loadingApiStatus")
fun bindStatus(statusImageView: ImageView, status: GithubApiStatus?) {
    when (status) {
        GithubApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        GithubApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        GithubApiStatus.DONE ->
            statusImageView.visibility = View.GONE
    }
}





