package com.chani.mylibrarykt.di.module

import android.content.Context
import android.provider.SearchRecentSuggestions
import androidx.room.Room
import com.chani.mylibrarykt.data.local.RecentHistoryDatabase
import com.chani.mylibrarykt.data.local.dao.RecentHistoryDao
import com.chani.mylibrarykt.provider.QuickSearchProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideSearchRecentSuggestions(@ApplicationContext ctx: Context): SearchRecentSuggestions {
        return SearchRecentSuggestions(ctx, QuickSearchProvider.AUTH, QuickSearchProvider.MODE)
    }

    @Provides
    fun provideUserDatabase(@ApplicationContext ctx: Context): RecentHistoryDao {
        return Room.databaseBuilder(ctx, RecentHistoryDatabase::class.java, "RecentHistory")
            .build()
            .getRecentHistoryDao()
    }
}