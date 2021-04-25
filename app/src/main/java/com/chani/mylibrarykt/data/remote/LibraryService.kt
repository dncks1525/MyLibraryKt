/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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