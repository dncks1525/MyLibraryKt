package com.chani.mylibrarykt.data.local.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.data.local.HistoryDao
import com.chani.mylibrarykt.util.AppLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecentBooksPagingSource(
    private val dao: HistoryDao
) : PagingSource<Int, List<History>>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<History>> {
        return try {
            withContext(Dispatchers.IO) {
                LoadResult.Page(dao.getRecentBooks(), null, null)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, List<History>>): Int? {
        return state.anchorPosition
    }
}