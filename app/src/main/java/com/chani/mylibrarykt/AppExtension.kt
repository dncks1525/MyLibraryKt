package com.chani.mylibrarykt

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

fun ImageView.toByteArray(): ByteArray? {
    val bitmapDrawable = drawable as? BitmapDrawable
    if (bitmapDrawable != null) {
        ByteArrayOutputStream().apply {
            bitmapDrawable.bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            return toByteArray()
        }
    }

    return null
}

@BindingAdapter("app:bindImage")
fun ImageView.bindImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.book_placeholder)
        .centerCrop()
        .thumbnail(0.3f)
        .into(this)
}