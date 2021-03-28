package com.chani.mylibrarykt.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.data.entity.Book
import com.chani.mylibrarykt.data.repository.datasource.NewBooksDataSource
import com.chani.mylibrarykt.data.repository.datasource.SearchDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookstoreRepository @Inject constructor(
    private val api: BookstoreApi
) {
    fun getNewBooks(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { NewBooksDataSource(api) }
        ).flow
    }

    fun search(): Flow<PagingData<Book>> {
        return Pager(
            config = PagingConfig(pageSize = 1),
            pagingSourceFactory = { SearchDataSource(api, "kotlin") }
        ).flow
    }
}