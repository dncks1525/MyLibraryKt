package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.data.repository.BookstoreRepository
import com.chani.mylibrarykt.data.repository.remote.BookstoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun getBookstoreRepository(bookstoreApi: BookstoreApi) = BookstoreRepository(bookstoreApi)
}