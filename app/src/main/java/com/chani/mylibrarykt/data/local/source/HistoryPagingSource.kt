package com.chani.mylibrarykt.data.local.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.local.History

class HistoryPagingSource(
    private val histories: List<History>
) : PagingSource<Int, History>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, History> {
        return try {
            val page = params.key ?: 1
            LoadResult.Page(
                data = histories,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page + 1 < histories.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, History>): Int? {
        return state.anchorPosition
    }
}