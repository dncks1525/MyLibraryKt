package com.chani.mylibrarykt.data.local

import androidx.room.*

@Dao
abstract class HistoryDao {
    @Transaction
    open fun getRecentBooks(): List<List<History>> {
        return mutableListOf<List<History>>().apply {
            getDates().forEach {
                add(getHistoriesBy(it))
            }
        }
    }

    @Query("SELECT * FROM History WHERE date(timestamp/1000, 'unixepoch') = :date ORDER BY timestamp DESC")
    abstract fun getHistoriesBy(date: String): List<History>

    @Query("SELECT DISTINCT date(timestamp/1000, 'unixepoch') as date FROM History ORDER BY date DESC")
    abstract fun getDates(): List<String>

    @Query("SELECT * FROM History ORDER BY timestamp DESC LIMIT 1")
    abstract fun getLastHistory(): History?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(history: History)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(histories: List<History>)

    @Delete
    abstract fun delete(history: History)

    @Query("DELETE FROM History")
    abstract fun deleteAll()
}