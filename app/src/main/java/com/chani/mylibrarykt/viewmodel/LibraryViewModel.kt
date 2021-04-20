package com.chani.mylibrarykt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.data.source.SearchBookPagingSource
import com.chani.mylibrarykt.data.source.NewBookPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
) : ViewModel() {
    fun fetchNewBooks(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { NewBookPagingSource(libraryRepository) }
        ).flow
            .cachedIn(viewModelScope)
    }

    fun fetchSearchResult(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { SearchBookPagingSource(libraryRepository, query) }
        ).flow
            .cachedIn(viewModelScope)
    }
}