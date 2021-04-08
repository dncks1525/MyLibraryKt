package com.chani.mylibrarykt.data.repository.local.converter

import androidx.room.TypeConverter
import com.chani.mylibrarykt.data.repository.remote.model.Book
import com.chani.mylibrarykt.data.repository.remote.model.BookDetail
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
        val itemType = object : TypeToken<List<Book>>() {}.type
        return Gson().fromJson(json, itemType)
    }

    @TypeConverter
    fun fromBooks(books: List<Book>): String {
        return Gson().toJson(books)
    }
}