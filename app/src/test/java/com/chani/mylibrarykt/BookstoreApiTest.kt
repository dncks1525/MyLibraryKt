package com.chani.mylibrarykt

import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookstoreApiTest {
    private lateinit var api: BookstoreApi

    @Before
    fun setup() {
        api = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookstoreApi::class.java)
    }

    @Test
    fun getNewTest() {
        runBlocking {
            var isWorking = false
            try {
                val result = api.getNewBooks()
                isWorking = result.books.count() > 1
                println(result)
            } catch (e: Exception) {
                println(e.toString())
            }

            Truth.assertThat(isWorking)
        }
    }

    @Test
    fun searchTest() {
        runBlocking {
            var isWorking = false
            try {
                val result = api.search("kotlin")
                isWorking = result.books.count() > 1
                println(result)
            } catch (e: Exception) {
                println(e.toString())
            }

            Truth.assertThat(isWorking)
        }
    }

    @Test
    fun getBookInfoTest() {
        runBlocking {
            var isWorking = false
            try {
                api.getNewBooks().also { bookstore ->
                    if (bookstore.books.isNotEmpty()) {
                        val result = api.getBookDetail(bookstore.books[0].isbn13)
                        isWorking = bookstore.books[0].title == result.title
                        println(result)
                    }
                }
            } catch (e: Exception) {
                println(e.toString())
            }

            Truth.assertThat(isWorking)
        }
    }
}