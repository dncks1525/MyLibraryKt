package com.chani.mylibrarykt.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.network.ItBookstoreApi
import com.chani.mylibrarykt.network.model.Book

class SearchBookDataSource(
    private val api: ItBookstoreApi,
    private val query: String
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val page = params.key ?: 1
            val bookstore = api.search(query, page)
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