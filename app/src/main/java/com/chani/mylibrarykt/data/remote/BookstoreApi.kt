package com.chani.mylibrarykt.data.remote

import com.chani.mylibrarykt.data.remote.model.BookDetail
import com.chani.mylibrarykt.data.remote.model.Books
import retrofit2.http.GET
import retrofit2.http.Path

interface BookstoreApi {
    @GET("new")
    suspend fun getNewBooks(): Books

    @GET("search/{query}/{page}")
    suspend fun search(
        @Path("query") query: String,
        @Path("page") page: Int = 0
    ): Books

    @GET("books/{isbn13}")
    suspend fun getBookDetail(@Path("isbn13") isbn: String): BookDetail
}