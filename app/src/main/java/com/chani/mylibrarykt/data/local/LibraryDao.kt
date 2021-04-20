package com.chani.mylibrarykt.data.local

import androidx.room.*
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.data.model.LibraryResponse

@Dao
abstract class LibraryDao {
    @Transaction
    open fun getLibraryResponsesByDate(): List<LibraryResponse> {
        return mutableListOf<LibraryResponse>().apply {
            getAllDates().forEach { add(LibraryResponse(getBooksBy(it))) }
        }
    }

    @Query("SELECT * FROM Library ORDER BY timestamp DESC LIMIT 1")
    abstract fun getBookLast(): Book?

    @Query("SELECT * FROM Library WHERE date(timestamp/1000, 'unixepoch') = :date ORDER BY timestamp DESC")
    abstract fun getBooksBy(date: String): List<Book>

    @Query("SELECT DISTINCT date(timestamp/1000, 'unixepoch') as date FROM Library ORDER BY date DESC")
    abstract fun getAllDates(): List<String>

    @Query("DELETE FROM Library")
    abstract fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg books: Book)

    @Delete
    abstract fun delete(vararg books: Book)
}