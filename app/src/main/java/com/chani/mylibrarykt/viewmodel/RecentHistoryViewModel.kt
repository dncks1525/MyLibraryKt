package com.chani.mylibrarykt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.repository.HistoryRepository
import com.chani.mylibrarykt.data.repository.local.entity.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecentHistoryViewModel @Inject constructor(
    private val repository: HistoryRepository
) : ViewModel() {
    fun getHistories(): Flow<PagingData<List<History>>> {
        return repository.getHistories().cachedIn(viewModelScope)
    }
}