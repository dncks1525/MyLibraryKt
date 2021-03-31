package com.chani.mylibrarykt.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.data.remote.repository.pagingsource.NewBooksPagingSource
import com.chani.mylibrarykt.data.remote.repository.pagingsource.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookstoreRepository @Inject constructor(
    private val api: BookstoreApi
) {
    fun getNewBooks(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { NewBooksPagingSource(api) }
        ).flow
    }

    fun search(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow
    }
}