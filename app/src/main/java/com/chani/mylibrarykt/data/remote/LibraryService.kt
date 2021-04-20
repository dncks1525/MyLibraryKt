package com.chani.mylibrarykt.data.remote

import com.chani.mylibrarykt.data.model.BookDetail
import com.chani.mylibrarykt.data.model.LibraryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LibraryService {
    @GET("new")
    suspend fun fetchNewBooks(): LibraryResponse

    @GET("search/{query}/{page}")
    suspend fun fetchSearchResult(
        @Path("query") query: String,
        @Path("page") page: Int =0
    ): LibraryResponse

    @GET("books/{isbn13}")
    suspend fun fetchBookDetail(@Path("isbn13") isbn13: String): BookDetail
}