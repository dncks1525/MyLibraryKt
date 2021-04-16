package com.chani.mylibrarykt.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["isbn13"], unique = true)])
data class History(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val timestamp: Long,
    val imgPath: String,
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
)