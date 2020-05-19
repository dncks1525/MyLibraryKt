package com.chani.mylibrarykt

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class BookStoreApi(ctx: Context) {
    var bookRepo: IBookRepo

    init {
        println("init")
        val client = OkHttpClient.Builder()
            .cache(Cache(ctx.cacheDir, 10 * 1024 * 1024))
            .build()

        bookRepo = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IBookRepo::class.java)
    }

    interface IBookRepo {
        @GET("new")
        suspend fun getNewBook(): BookData

        @GET("books/{isbn13}")
        suspend fun getBookDetail(@Path("isbn13") isbn13: String): BookDetail

        @GET("search/{query}/{page}")
        suspend fun search(@Path("query") query: String, @Path("page") page: Int): BookData
    }

    data class BookData(val error: Int, val total: Int, val books: MutableList<Book>)

    data class Book(
        val title: String,
        val subtitle: String,
        val isbn13: String,
        val price: String,
        val image: String,
        val url: String,
        var isBookmarked: Boolean
    )

    data class BookDetail(
        val error: Int,
        val string: String,
        val subtitle: String,
        val authors: String,
        val publisher: String,
        val language: String,
        val isbn10: String,
        val isbn13: String,
        val pages: Int,
        val year: Int,
        val rating: Int,
        val desc: String,
        val price: String,
        val image: String,
        val url: String,
        val memo: String
    )
}