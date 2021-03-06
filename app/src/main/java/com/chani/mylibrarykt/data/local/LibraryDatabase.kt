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

package com.chani.mylibrarykt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chani.mylibrarykt.data.model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class LibraryDatabase: RoomDatabase() {
    abstract fun getLibraryDao(): LibraryDao
}