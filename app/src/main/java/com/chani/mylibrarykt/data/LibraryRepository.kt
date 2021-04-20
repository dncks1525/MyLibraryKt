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