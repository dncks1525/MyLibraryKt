package com.chani.mylibrarykt.data.repository.local.dao

import androidx.room.*
import com.chani.mylibrarykt.data.repository.local.entity.History

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY timestamp DESC")
    fun getHistories(): List<History>

    @Query("SELECT * FROM History ORDER BY timestamp DESC LIMIT 1")
    fun getLastHistory(): History?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: History)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(histories: List<History>)

    @Delete
    fun delete(history: History)

    @Delete
    fun deleteAll(histories: List<History>)
}