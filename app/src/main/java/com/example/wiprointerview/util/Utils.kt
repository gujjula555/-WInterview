package com.example.wiprointerview.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.wiprointerview.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url?.replace("http", "https"))
            .error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background)
        .into(imageView)
}

