/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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