package com.franjo.github.presentation.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.franjo.github.domain.shared.LoadingApiStatus
import com.franjo.github.presentation.R
import com.franjo.github.presentation.features.user_details.user.UserDetailsAdapter
import com.franjo.github.presentation.model.UserDataRowItem

@BindingAdapter("userDetailsList")
fun bindUserDetailsList(recyclerView: RecyclerView, list: List<UserDataRowItem>?) {
    val adapter: UserDetailsAdapter = recyclerView.adapter as UserDetailsAdapter
    if (list != null) {
        adapter.submitList(list)
    }
}

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
@BindingAdapter("loadingStatus", "errorStatus")
fun bindStatus(statusProgressBar: ProgressBar, progressStatus: LoadingApiStatus?, statusImageView: ImageView) {
    when (progressStatus) {
        LoadingApiStatus.LOADING -> {
            statusProgressBar.visibility = View.VISIBLE
        }
        LoadingApiStatus.ERROR -> {
            statusProgressBar.visibility = View.GONE
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        LoadingApiStatus.DONE ->
            statusProgressBar.visibility = View.GONE
    }
}





