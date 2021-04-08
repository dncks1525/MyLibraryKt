package com.chani.mylibrarykt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.data.repository.local.source.HistoryPagingSource
import kotlinx.coroutines.flow.Flow

class HistoryRepository(
    private val histories: List<History>
) {
    fun getHistories(): Flow<PagingData<History>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { HistoryPagingSource(histories) }
        ).flow
    }
}