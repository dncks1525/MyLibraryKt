package com.chani.mylibrarykt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chani.mylibrarykt.data.remote.model.Book

@Entity
data class User(
    val book: Book,
    val timestamp: Long,
) {
    @PrimaryKey
    var isbn13: String = book.isbn13
}