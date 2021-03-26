package com.chani.mylibrarykt.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.network.ItBookstoreApi
import com.chani.mylibrarykt.network.model.Book

class NewBookDataSource(
    private val api: ItBookstoreApi
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            LoadResult.Page(
                data = api.getNewBooks().books,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
}