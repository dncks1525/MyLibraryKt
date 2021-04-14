package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.data.remote.BookstoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object RemoteModule {
    @Provides
    fun provideBookstoreApi() = Retrofit.Builder()
        .baseUrl("https://api.itbook.store/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookstoreApi::class.java)
}