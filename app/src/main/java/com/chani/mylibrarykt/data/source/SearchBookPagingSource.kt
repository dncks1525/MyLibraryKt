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

package com.chani.mylibrarykt.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.Book

class SearchBookPagingSource(
    private val libraryRepository: LibraryRepository,
    private val query: String,
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val page = params.key ?: 1
            val bookResponse = libraryRepository.fetchSearchResult(query, page)
            LoadResult.Page(
                data = bookResponse.books,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page + 1 < bookResponse.total) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
}