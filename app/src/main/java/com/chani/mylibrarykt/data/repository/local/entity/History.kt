package com.chani.mylibrarykt.data.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chani.mylibrarykt.data.repository.remote.model.Book
import com.chani.mylibrarykt.data.repository.remote.model.BookDetail

@Entity
data class History(
    val date: String,
    val timestamp: Long,
    val coverImgPath: String,
    val book: Book
) {
    @PrimaryKey
    var isbn13: String = book.isbn13
}