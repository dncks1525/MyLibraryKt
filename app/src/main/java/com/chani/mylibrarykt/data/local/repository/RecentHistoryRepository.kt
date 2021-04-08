package com.chani.mylibrarykt.data.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chani.mylibrarykt.data.local.dao.RecentHistoryDao
import com.chani.mylibrarykt.data.local.entity.RecentHistory
import com.chani.mylibrarykt.data.local.repository.pagingsource.RecentHistoryPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecentHistoryRepository @Inject constructor(
    private val recentHistoryDao: RecentHistoryDao
) {
    fun getRecentHistories(): Flow<PagingData<RecentHistory>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { RecentHistoryPagingSource(recentHistoryDao) }
        ).flow
    }
}