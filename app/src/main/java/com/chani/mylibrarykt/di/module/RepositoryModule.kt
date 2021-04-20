package com.chani.mylibrarykt.di.module

import android.content.Context
import androidx.room.Room
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.local.LibraryDao
import com.chani.mylibrarykt.data.local.LibraryDatabase
import com.chani.mylibrarykt.data.remote.LibraryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideLibraryService() = Retrofit.Builder()
        .baseUrl("https://api.itbook.store/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LibraryService::class.java)

    @Provides
    @ActivityRetainedScoped
    fun provideLibraryDao(@ApplicationContext ctx: Context) =
        Room.databaseBuilder(ctx, LibraryDatabase::class.java, "Library")
            .build()
            .getLibraryDao()

    @Provides
    @ActivityRetainedScoped
    fun provideLibraryRepository(
        libraryService: LibraryService,
        libraryDao: LibraryDao
    ): LibraryRepository {
        return LibraryRepository(libraryService, libraryDao)
    }
}