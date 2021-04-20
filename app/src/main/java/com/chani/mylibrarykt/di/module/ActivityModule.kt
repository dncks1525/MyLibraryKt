package com.chani.mylibrarykt.di.module

import android.content.Context
import android.provider.SearchRecentSuggestions
import com.chani.mylibrarykt.databinding.*
import com.chani.mylibrarykt.provider.QuickSearchProvider
import com.chani.mylibrarykt.ui.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @ActivityScoped
    fun provideMainActivity(@ActivityContext ctx: Context): ActivityMainBinding {
        return ActivityMainBinding.inflate((ctx as MainActivity).layoutInflater)
    }

    @Provides
    @ActivityScoped
    fun provideBookDetailActivity(@ActivityContext ctx: Context): ActivityBookDetailBinding {
        return ActivityBookDetailBinding.inflate((ctx as BookDetailActivity).layoutInflater)
    }

    @Provides
    @ActivityScoped
    fun provideBookSearchActivity(@ActivityContext ctx: Context): ActivityBookSearchBinding {
        return ActivityBookSearchBinding.inflate((ctx as BookSearchActivity).layoutInflater)
    }

    @Provides
    @ActivityScoped
    fun provideBookCollectionActivity(@ActivityContext ctx: Context): ActivityBookCollectionBinding {
        return ActivityBookCollectionBinding.inflate((ctx as BookCollectionActivity).layoutInflater)
    }

    @Provides
    @ActivityScoped
    fun provideHistoryActivity(@ActivityContext ctx: Context): ActivityRecentBooksBinding {
        return ActivityRecentBooksBinding.inflate((ctx as RecentBooksActivity).layoutInflater)
    }

    @Provides
    @ActivityScoped
    fun provideSearchRecentSuggestions(@ApplicationContext ctx: Context): SearchRecentSuggestions {
        return SearchRecentSuggestions(ctx, QuickSearchProvider.AUTH, QuickSearchProvider.MODE)
    }
}