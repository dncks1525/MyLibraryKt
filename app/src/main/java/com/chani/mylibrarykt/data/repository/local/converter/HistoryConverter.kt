package com.chani.mylibrarykt.data.repository.local.converter

import androidx.room.TypeConverter
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.data.repository.remote.model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryConverter {
    @TypeConverter
    fun toBook(json: String): Book {
        return Gson().fromJson(json, Book::class.java)
    }

    @TypeConverter
    fun fromBook(book: Book): String {
        return Gson().toJson(book)
    }

    @TypeConverter
    fun toBooks(json: String): List<Book> {
        return Gson().fromJson(json, object : TypeToken<List<Book>>() {}.type)
    }

    @TypeConverter
    fun fromBooks(books: List<Book>): String {
        return Gson().toJson(books)
    }

    @TypeConverter
    fun toHistories(json: String): List<History> {
        return Gson().fromJson(json, object : TypeToken<List<History>>() {}.type)
    }

    @TypeConverter
    fun fromHistories(histories: List<History>): String {
        return Gson().toJson(histories)
    }
}