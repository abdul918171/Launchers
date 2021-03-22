package com.spacex.spacexlaunchers.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.spacex.spacexlaunchers.R

@BindingAdapter("imageUrl")
fun setImageUrl(img: ImageView, url: String?) {

    url?.let {

        loadImage(img.context, img, it, R.drawable.ic_launcher_background, retry = false)

    }
}