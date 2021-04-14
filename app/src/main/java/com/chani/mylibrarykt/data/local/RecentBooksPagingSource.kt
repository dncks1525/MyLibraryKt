package com.chani.mylibrarykt.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState

class RecentBooksPagingSource(
    private val dao: HistoryDao
) : PagingSource<Int, List<History>>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<History>> {
        return try {
            val page = params.key ?: 1
            val histories = dao.getRecentBooks()
            LoadResult.Page(
                data = histories,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page + 1 < histories.size) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, List<History>>): Int? {
        return state.anchorPosition
    }
}