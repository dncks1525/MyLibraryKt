package com.chani.mylibrarykt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}