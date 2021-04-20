package com.chani.mylibrarykt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chani.mylibrarykt.data.model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class LibraryDatabase: RoomDatabase() {
    abstract fun getLibraryDao(): LibraryDao
}