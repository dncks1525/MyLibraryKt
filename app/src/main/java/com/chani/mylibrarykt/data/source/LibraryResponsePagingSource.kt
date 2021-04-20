package com.chani.mylibrarykt.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.LibraryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LibraryResponsePagingSource(
    private val libraryRepository: LibraryRepository
) : PagingSource<Int, LibraryResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LibraryResponse> {
        return try {
            withContext(Dispatchers.IO) {
                LoadResult.Page(libraryRepository.getLibraryResponsesByDate(), null, null)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LibraryResponse>): Int? {
        return state.anchorPosition
    }
}