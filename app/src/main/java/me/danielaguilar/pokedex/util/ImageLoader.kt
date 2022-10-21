package me.danielaguilar.pokedex.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {
    fun loadImage(url: String, context: Context, target: ImageView) {
        Glide.with(context).load(url).into(target)
    }
}