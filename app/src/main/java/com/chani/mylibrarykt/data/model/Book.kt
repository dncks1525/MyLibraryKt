package com.chani.mylibrarykt.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Library",
    indices = [Index(value = ["isbn13"], unique = true)]
)
data class Book(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String,
    val localImgPath: String? = null,
    val timestamp: Long = 0L,
    @PrimaryKey(autoGenerate = true) val bookId: Long = 0L,
)