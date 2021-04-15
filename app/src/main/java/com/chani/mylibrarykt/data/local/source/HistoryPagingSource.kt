package com.chani.mylibrarykt.data.local.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.util.AppLog

class HistoryPagingSource(
    private val histories: List<History>
) : PagingSource<Int, History>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, History> {
        return try {
            LoadResult.Page(histories, null, null)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, History>): Int? {
        return state.anchorPosition
    }
}