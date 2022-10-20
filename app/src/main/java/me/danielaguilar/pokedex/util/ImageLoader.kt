package me.danielaguilar.pokedex.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

class ImageLoader {
    fun loadImage(url: String, context: Context, target: ImageView) {
        Glide.with(context).load(url).into(target)
    }
}