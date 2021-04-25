/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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