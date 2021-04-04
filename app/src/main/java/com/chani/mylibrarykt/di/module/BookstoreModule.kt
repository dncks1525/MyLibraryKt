package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.data.remote.repository.BookstoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object BookstoreModule {
    @Provides
    @ViewModelScoped
    fun getBookstoreRepository(api: BookstoreApi) = BookstoreRepository(api)
}