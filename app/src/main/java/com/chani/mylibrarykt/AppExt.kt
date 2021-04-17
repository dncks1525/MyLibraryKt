package com.chani.mylibrarykt

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.data.remote.model.Book
import java.io.ByteArrayOutputStream

fun Book.convertTo(timestamp: Long, imgPath: String): History {
    return History(0, timestamp, imgPath, title, subtitle, isbn13, price)
}

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