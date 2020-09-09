package com.example.movieshub.app.databinding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.squareup.picasso.Picasso


@BindingConversion
fun setVisibility(state: Boolean): Int {
    return if(state) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun loadImage(imgView: ImageView, url: String?) {
    if(url != null) {
        Picasso.get().load(url).into(imgView)
    }
}