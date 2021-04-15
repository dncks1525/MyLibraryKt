package com.chani.mylibrarykt.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.util.AppLog
import java.lang.Exception

class NewBooksPagingSource(
    private val api: BookstoreApi
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val page = params.key ?: 1
            val books = api.getNewBooks().books
            AppLog.d("source books = ${books.size}")

            LoadResult.Page(
                data = books,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page + 1 < books.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
}