package com.example.wiprointerview.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ImageView
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.wiprointerview.R


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
       Glide.with(imageView.context).load(url).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(imageView)

}
@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}
