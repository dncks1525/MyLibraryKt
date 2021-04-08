package com.chani.mylibrarykt.di.module

import android.content.Context
import androidx.room.Room
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.data.repository.local.HistoryDatabase
import com.chani.mylibrarykt.data.repository.local.dao.HistoryDao
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.data.repository.remote.BookstoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun getBookstoreApi() = Retrofit.Builder()
        .baseUrl(AppConst.IT_BOOK_STORE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookstoreApi::class.java)

    @Provides
    fun provideHistoryDao(@ApplicationContext ctx: Context): HistoryDao {
        return Room.databaseBuilder(ctx, HistoryDatabase::class.java, "History")
            .build()
            .getHistoryDao()
    }
}