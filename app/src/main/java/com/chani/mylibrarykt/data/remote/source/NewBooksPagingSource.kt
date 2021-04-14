package com.chani.mylibrarykt.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.BookstoreApi
import java.lang.Exception

class NewBooksPagingSource(
    private val api: BookstoreApi
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            LoadResult.Page(data = api.getNewBooks().books, null, null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
}