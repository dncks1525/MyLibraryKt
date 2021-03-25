package com.chani.mylibrarykt.repository

import com.chani.mylibrarykt.data.Books
import retrofit2.http.GET
import retrofit2.http.Path

interface ItBookstoreApi {
    @GET("new")
    suspend fun getNewBooks(): Books

    @GET("search/{query}/{page}")
    suspend fun search(
        @Path("query") query: String,
        @Path("page") page: Int = 0
    ): Books

    @GET("books/{isbn13}")
    suspend fun getBookInfo(@Path("isbn13") isbn: String)
}