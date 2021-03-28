package com.chani.mylibrarykt.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.entity.Book
import com.chani.mylibrarykt.data.repository.BookstoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookstoreViewModel @Inject constructor(
    private val repository: BookstoreRepository
) : ViewModel() {
    fun getNewBooks(): Flow<PagingData<Book>> {
        return repository.getNewBooks().cachedIn(viewModelScope)
    }

    fun search(): Flow<PagingData<Book>> {
        return repository.search().cachedIn(viewModelScope)
    }
}