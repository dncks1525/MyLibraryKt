package com.chani.mylibrarykt

import com.chani.mylibrarykt.data.remote.LibraryService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryServiceTest {
    private lateinit var libraryService: LibraryService

    @Before
    fun setup() {
        libraryService = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryService::class.java)
    }

    @Test
    fun fetchNewTest() {
        runBlocking {
            libraryService.fetchNewBooks().also { bookResponse ->
                assertThat(bookResponse).isNotNull()
                assertThat(bookResponse.books).isNotEmpty()
            }
        }
    }

    @Test
    fun fetchSearchResultTest() {
        runBlocking {
            libraryService.fetchSearchResult("kotlin").also { bookResponse ->
                assertThat(bookResponse).isNotNull()
                assertThat(bookResponse.books).isNotEmpty()
            }
        }
    }

    @Test
    fun fetchBookDetailTest() {
        runBlocking {
            val book = libraryService.fetchNewBooks().books.first()
            libraryService.fetchBookDetail(book.isbn13).also { bookDetail ->
                assertThat(bookDetail).isNotNull()
                assertThat(bookDetail.isbn13).isEqualTo(book.isbn13)
            }
        }
    }
}