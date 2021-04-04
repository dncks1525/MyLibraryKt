package com.chani.mylibrarykt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chani.mylibrarykt.data.local.converter.UserConverter
import com.chani.mylibrarykt.data.local.dao.UserDao
import com.chani.mylibrarykt.data.local.entity.User

@Database(entities = [User::class], version = 1)
@TypeConverters(UserConverter::class)
abstract class UserDb : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}