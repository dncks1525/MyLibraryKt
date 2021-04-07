package com.chani.mylibrarykt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.model.BookDetail

@Entity
data class User(
    val bookDetail: BookDetail,
    val coverImgPath: String,
    val timestamp: Long,
) {
    @PrimaryKey
    var isbn13: String = bookDetail.isbn13
}