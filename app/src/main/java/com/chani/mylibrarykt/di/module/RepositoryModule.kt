package com.chani.mylibrarykt.di.module

import android.content.Context
import androidx.room.Room
import com.chani.mylibrarykt.data.local.HistoryDatabase
import com.chani.mylibrarykt.data.remote.BookstoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    fun provideBookstoreApi() = Retrofit.Builder()
        .baseUrl("https://api.itbook.store/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookstoreApi::class.java)

    @Provides
    fun provideHistoryDao(@ApplicationContext ctx: Context) =
        Room.databaseBuilder(ctx, HistoryDatabase::class.java, "History")
            .build()
            .getHistoryDao()
}