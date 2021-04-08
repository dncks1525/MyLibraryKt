package com.chani.mylibrarykt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.repository.HistoryRepository
import com.chani.mylibrarykt.data.repository.local.entity.History
import kotlinx.coroutines.flow.Flow

class RecentHistoryViewModel(
    private val repository: HistoryRepository
) : ViewModel() {
    fun getHistories(): Flow<PagingData<History>> {
        return repository.getHistories().cachedIn(viewModelScope)
    }
}