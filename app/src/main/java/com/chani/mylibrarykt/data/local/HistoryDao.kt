package com.chani.mylibrarykt.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class HistoryDao {
    @Insert
    abstract fun insert(history: History)

    @Insert
    abstract fun insertAll(histories: List<History>)

    @Delete
    abstract fun delete(history: History)

    @Query("DELETE FROM History")
    abstract fun deleteAll()
}