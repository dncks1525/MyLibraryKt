/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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