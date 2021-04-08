package com.chani.mylibrarykt.data.local.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chani.mylibrarykt.data.local.dao.RecentHistoryDao
import com.chani.mylibrarykt.data.local.entity.RecentHistory

class RecentHistoryPagingSource(
    private val recentHistoryDao: RecentHistoryDao
) : PagingSource<Int, RecentHistory>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecentHistory> {
        return try {
            val page = params.key ?: 1
            val history = recentHistoryDao.getAll()
            LoadResult.Page(
                data = history,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page + 1 < history.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecentHistory>): Int? {
        return state.anchorPosition
    }
}