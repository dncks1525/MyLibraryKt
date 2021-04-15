package com.chani.mylibrarykt.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.util.AppLog

class SearchPagingSource(
    private val api: BookstoreApi,
    private val query: String,
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val page = params.key ?: 1
            val bookstore = api.search(query, page)
            AppLog.d("SearchSource page ${bookstore.page} ${bookstore.total}")

            LoadResult.Page(
                data = bookstore.books,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page + 1 < bookstore.total) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
}