package com.chani.mylibrarykt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.data.local.HistoryDao
import com.chani.mylibrarykt.data.local.source.HistoryPagingSource
import com.chani.mylibrarykt.data.local.source.RecentBooksPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecentBooksViewModel @Inject constructor(
    private val dao: HistoryDao
) : ViewModel() {
    fun getRecentBooks(): Flow<PagingData<List<History>>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { RecentBooksPagingSource(dao) }
        ).flow
            .cachedIn(viewModelScope)
    }

    fun transformHistories(histories: List<History>): Flow<PagingData<History>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { HistoryPagingSource(histories) }
        ).flow
            .cachedIn(viewModelScope)
    }
}