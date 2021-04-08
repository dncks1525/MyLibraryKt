package com.chani.mylibrarykt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chani.mylibrarykt.data.local.converter.RecentHistoryConverter
import com.chani.mylibrarykt.data.local.dao.RecentHistoryDao
import com.chani.mylibrarykt.data.local.entity.RecentHistory

@Database(entities = [RecentHistory::class], version = 1)
@TypeConverters(RecentHistoryConverter::class)
abstract class RecentHistoryDatabase : RoomDatabase() {
    abstract fun getRecentHistoryDao(): RecentHistoryDao
}