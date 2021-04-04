package com.chani.mylibrarykt.di.module

import android.content.Context
import android.provider.SearchRecentSuggestions
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.adapter.BooksAdapter
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.provider.QuickSearchProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideSearchRecentSuggestions(@ApplicationContext ctx: Context): SearchRecentSuggestions {
        return SearchRecentSuggestions(ctx, QuickSearchProvider.AUTH, QuickSearchProvider.MODE)
    }
}