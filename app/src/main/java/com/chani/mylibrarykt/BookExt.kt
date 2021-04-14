package com.chani.mylibrarykt

import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.data.remote.model.Book

fun Book.convertTo(timestamp: Long, imgPath: String): History {
    return History(0, timestamp, imgPath, title, subtitle, isbn13, price)
}