package com.chani.mylibrarykt

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.chani.mylibrarykt.data.BookStoreApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RestApiTest {
    val context by lazy { ApplicationProvider.getApplicationContext() as Application }
    val bookRepo by lazy { BookStoreApi(context).bookRepo }

    @Before
    fun setup() {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
        assertThat(context).isNotNull()
        assertThat(bookRepo).isNotNull()
    }

    @Test
    fun newTest() = runBlocking {
        val book = bookRepo.getNewBook().books
        assertThat(book).isNotNull()
    }

    @Test
    fun searchTest() = runBlocking {
        val book = bookRepo.search("android", 0).books
        assertThat(book).isNotNull()

        if (book.size > 0) {
            val bookDetail = bookRepo.getBookDetail(book[0].isbn13)
            assertThat(bookDetail).isNotNull()
        }
    }
}