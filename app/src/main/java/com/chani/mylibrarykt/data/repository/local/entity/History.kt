package com.chani.mylibrarykt.data.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chani.mylibrarykt.data.repository.remote.model.Book
import com.chani.mylibrarykt.data.repository.remote.model.BookDetail

@Entity
data class History(
    val book: Book,
    val coverImgPath: String,
    val timestamp: Long,
) {
    @PrimaryKey
    var isbn13: String = book.isbn13
}