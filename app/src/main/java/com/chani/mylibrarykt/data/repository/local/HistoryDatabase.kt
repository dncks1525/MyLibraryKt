package com.chani.mylibrarykt.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chani.mylibrarykt.data.repository.local.converter.HistoryConverter
import com.chani.mylibrarykt.data.repository.local.dao.HistoryDao
import com.chani.mylibrarykt.data.repository.local.entity.History

@Database(entities = [History::class], version = 1)
@TypeConverters(HistoryConverter::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}