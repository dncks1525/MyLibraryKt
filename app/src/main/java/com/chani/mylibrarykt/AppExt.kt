package com.chani.mylibrarykt

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.data.model.BookDetail
import java.io.ByteArrayOutputStream
import java.util.*

fun BookDetail.toBook(localImgPath: String): Book {
    return Book(title, subtitle, isbn13, price, image, url, localImgPath, Calendar.getInstance().timeInMillis)
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