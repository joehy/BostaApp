package com.example.joebosta.adabter

import android.media.Image
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(imageView.context)
            .load(it)
            .into(imageView)
    }
}
