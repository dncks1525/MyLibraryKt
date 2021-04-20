package com.chani.mylibrarykt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.data.model.LibraryResponse
import com.chani.mylibrarykt.data.source.BookPagingSource
import com.chani.mylibrarykt.data.source.LibraryResponsePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RecentBooksViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
) : ViewModel() {
    fun getLibraryResponsesByDate(): Flow<PagingData<LibraryResponse>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { LibraryResponsePagingSource(libraryRepository) }
        ).flow
            .cachedIn(viewModelScope)
    }

    fun flowFrom(books: List<Book>): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { BookPagingSource(books) }
        ).flow
            .cachedIn(viewModelScope)
    }
}