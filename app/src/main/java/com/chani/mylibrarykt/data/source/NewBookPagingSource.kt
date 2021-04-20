package com.chani.mylibrarykt.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.Book

class NewBookPagingSource(
    private val libraryRepository: LibraryRepository
) : PagingSource<Int, Book>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            LoadResult.Page(libraryRepository.fetchNewBooks().books, null, null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition
    }
}