package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.data.remote.BookstoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun getBookstoreApi() = Retrofit.Builder()
        .baseUrl(AppConst.IT_BOOK_STORE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookstoreApi::class.java)
}