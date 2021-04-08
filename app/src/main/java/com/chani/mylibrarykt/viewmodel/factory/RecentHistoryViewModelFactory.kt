package com.chani.mylibrarykt.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chani.mylibrarykt.data.repository.HistoryRepository
import com.chani.mylibrarykt.viewmodel.RecentHistoryViewModel

class RecentHistoryViewModelFactory(
    private val repository: HistoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecentHistoryViewModel(repository) as T
    }
}