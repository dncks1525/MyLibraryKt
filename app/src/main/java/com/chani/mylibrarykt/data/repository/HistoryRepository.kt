package com.chani.mylibrarykt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chani.mylibrarykt.data.repository.local.dao.HistoryDao
import com.chani.mylibrarykt.data.repository.local.entity.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {
    fun getHistories(): Flow<PagingData<List<History>>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { historyDao.getHistories() }
        ).flow
    }
}