package com.chani.mylibrarykt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.source.NewBooksPagingSource
import com.chani.mylibrarykt.data.remote.source.SearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookstoreViewModel @Inject constructor(
    private val api: BookstoreApi
) : ViewModel() {
    fun getNewBooks(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { NewBooksPagingSource(api) }
        ).flow
            .cachedIn(viewModelScope)
    }

    fun search(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow
            .cachedIn(viewModelScope)
    }
}