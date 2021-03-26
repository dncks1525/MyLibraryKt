package com.chani.mylibrarykt

import com.chani.mylibrarykt.network.ItBookstoreApi
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class ItBookstoreApiTest {
    private lateinit var itBookstoreApi: ItBookstoreApi

    @Before
    fun setup() {
        itBookstoreApi = Retrofit.Builder()
            .baseUrl(MyLibraryConst.IT_BOOK_STORE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItBookstoreApi::class.java)
    }

    @Test
    fun getNewTest() {
        runBlocking {
            var isWorking = false
            try {
                val result = itBookstoreApi.getNewBooks()
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
                val result = itBookstoreApi.search("kotlin")
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
                itBookstoreApi.getNewBooks().also { bookstore ->
                    if (bookstore.books.isNotEmpty()) {
                        val result = itBookstoreApi.getBookInfo(bookstore.books[0].isbn13)
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