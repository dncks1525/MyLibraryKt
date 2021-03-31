package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.adapter.BookAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideBookstoreAdapter(): BookAdapter {
        return BookAdapter()
    }
}