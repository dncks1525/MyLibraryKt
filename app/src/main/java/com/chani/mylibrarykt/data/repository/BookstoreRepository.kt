package com.chani.mylibrarykt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chani.mylibrarykt.data.repository.remote.BookstoreApi
import com.chani.mylibrarykt.data.repository.remote.model.Book
import com.chani.mylibrarykt.data.repository.remote.source.NewBooksPagingSource
import com.chani.mylibrarykt.data.repository.remote.source.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookstoreRepository @Inject constructor(
    private val api: BookstoreApi
) {
    fun getNewBooks(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { NewBooksPagingSource(api) }
        ).flow
    }

    fun search(query: String): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow
    }
}