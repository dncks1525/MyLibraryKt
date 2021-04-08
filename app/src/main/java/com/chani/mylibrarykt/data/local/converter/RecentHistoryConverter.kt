package com.chani.mylibrarykt.data.local.converter

import androidx.room.TypeConverter
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.model.BookDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecentHistoryConverter {
    @TypeConverter
    fun toBookDetail(json: String): BookDetail {
        return Gson().fromJson(json, BookDetail::class.java)
    }

    @TypeConverter
    fun fromBookDetail(bookDetail: BookDetail): String {
        return Gson().toJson(bookDetail)
    }

    @TypeConverter
    fun toBooksDetails(json: String): List<BookDetail> {
        val itemType = object : TypeToken<List<BookDetail>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun fromBookDetails(bookDetails: List<Book>): String {
        return Gson().toJson(bookDetails)
    }
}