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

package com.chani.mylibrarykt.data

import com.chani.mylibrarykt.data.local.LibraryDao
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.data.model.BookDetail
import com.chani.mylibrarykt.data.model.LibraryResponse
import com.chani.mylibrarykt.data.remote.LibraryService
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val libraryService: LibraryService,
    private val libraryDao: LibraryDao,
) {
    suspend fun fetchNewBooks(): LibraryResponse {
        return libraryService.fetchNewBooks()
    }

    suspend fun fetchBookDetail(isbn13: String): BookDetail {
        return libraryService.fetchBookDetail(isbn13)
    }

    suspend fun fetchSearchResult(query: String, page: Int): LibraryResponse {
        return libraryService.fetchSearchResult(query, page)
    }

    fun getBookLast(): Book? {
        return libraryDao.getBookLast()
    }

    fun getLibraryResponsesByDate(): List<LibraryResponse> {
        return libraryDao.getLibraryResponsesByDate()
    }

    fun insert(vararg books: Book) {
        libraryDao.insert(*books)
    }

    fun delete(vararg books: Book) {
        libraryDao.delete(*books)
    }

    fun clear() {
        libraryDao.clear()
    }
}