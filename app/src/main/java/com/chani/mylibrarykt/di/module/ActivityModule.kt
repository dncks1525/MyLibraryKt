package com.chani.mylibrarykt.di.module

import android.content.Context
import android.provider.SearchRecentSuggestions
import com.chani.mylibrarykt.databinding.ActivityBookCollectionBinding
import com.chani.mylibrarykt.databinding.ActivityBookDetailBinding
import com.chani.mylibrarykt.databinding.ActivityBookSearchBinding
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.provider.QuickSearchProvider
import com.chani.mylibrarykt.ui.BookCollectionActivity
import com.chani.mylibrarykt.ui.BookDetailActivity
import com.chani.mylibrarykt.ui.BookSearchActivity
import com.chani.mylibrarykt.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideMainActivity(@ActivityContext ctx: Context): ActivityMainBinding {
        return ActivityMainBinding.inflate((ctx as MainActivity).layoutInflater)
    }

    @Provides
    fun provideBookDetailActivity(@ActivityContext ctx: Context): ActivityBookDetailBinding {
        return ActivityBookDetailBinding.inflate((ctx as BookDetailActivity).layoutInflater)
    }

    @Provides
    fun provideBookSearchActivity(@ActivityContext ctx: Context): ActivityBookSearchBinding {
        return ActivityBookSearchBinding.inflate((ctx as BookSearchActivity).layoutInflater)
    }

    @Provides
    fun provideBookCollectionActivity(@ActivityContext ctx: Context): ActivityBookCollectionBinding {
        return ActivityBookCollectionBinding.inflate((ctx as BookCollectionActivity).layoutInflater)
    }

    @Provides
    fun provideSearchRecentSuggestions(@ApplicationContext ctx: Context): SearchRecentSuggestions {
        return SearchRecentSuggestions(ctx, QuickSearchProvider.AUTH, QuickSearchProvider.MODE)
    }
}