package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.adapter.BookstoreAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideBookstoreAdapter(): BookstoreAdapter {
        return BookstoreAdapter()
    }
}