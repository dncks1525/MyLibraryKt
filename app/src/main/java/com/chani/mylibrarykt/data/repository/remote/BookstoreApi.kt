package com.chani.mylibrarykt.data.repository.remote

import com.chani.mylibrarykt.data.repository.remote.model.BookDetail
import com.chani.mylibrarykt.data.repository.remote.model.Bookstore
import retrofit2.http.GET
import retrofit2.http.Path

interface BookstoreApi {
    @GET("new")
    suspend fun getNewBooks(): Bookstore

    @GET("search/{query}/{page}")
    suspend fun search(
        @Path("query") query: String,
        @Path("page") page: Int = 0
    ): Bookstore

    @GET("books/{isbn13}")
    suspend fun getBookDetail(@Path("isbn13") isbn: String): BookDetail
}