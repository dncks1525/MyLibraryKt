package com.chani.mylibrarykt.data.local.dao

import androidx.room.*
import com.chani.mylibrarykt.data.local.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User ORDER BY timestamp ASC")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

    @Delete
    fun deleteAll(users: List<User>)
}