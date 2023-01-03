package com.example.pokepedia.ui.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.pokepedia.R

@BindingAdapter("imageViewUri")
fun bindImageView(imgView: ImageView, uri: String?) {
    uri?.let {
        val imgUri = uri.toUri()
            .buildUpon()
            .build()
        imgView.load(imgUri) {
            placeholder(R.drawable.rotating_pokeball)
            error(R.drawable.ic_baseline_broken_image_24)
        }
    }
}