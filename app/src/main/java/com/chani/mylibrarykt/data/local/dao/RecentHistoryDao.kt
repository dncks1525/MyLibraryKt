package com.chani.mylibrarykt.data.local.dao

import androidx.room.*
import com.chani.mylibrarykt.data.local.entity.RecentHistory

@Dao
interface RecentHistoryDao {
    @Query("SELECT * FROM RecentHistory ORDER BY timestamp DESC")
    fun getAll(): List<RecentHistory>

    @Query("SELECT * FROM RecentHistory ORDER BY timestamp DESC LIMIT 1")
    fun getLast(): RecentHistory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentHistory: RecentHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recentHistories: List<RecentHistory>)

    @Delete
    fun delete(recentHistory: RecentHistory)

    @Delete
    fun deleteAll(recentHistories: List<RecentHistory>)
}