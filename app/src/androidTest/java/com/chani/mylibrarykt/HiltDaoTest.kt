package com.chani.mylibrarykt

import android.content.Context
import androidx.room.Room
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.local.LibraryDao
import com.chani.mylibrarykt.data.local.LibraryDatabase
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.data.remote.LibraryService
import com.chani.mylibrarykt.di.module.RepositoryModule
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class HiltDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun insertTest() {
        runBlocking {
            with(libraryRepository) {
                val book = fetchNewBooks().books.first()
                insert(book)
                assertThat(getBookLast()?.isbn13).isEqualTo(book.isbn13)
                delete(book)
            }
        }
    }

    @Test
    fun recentBooksTest() {
        runBlocking {
            val timestamp = Calendar.getInstance().apply {
                set(Calendar.YEAR, 1980)
                set(Calendar.MONTH, 5)
                set(Calendar.DAY_OF_MONTH, 20)
            }.timeInMillis

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            with(libraryRepository) {
                val books = fetchNewBooks().books.take(3).let { newBooks ->
                    mutableListOf<Book>().apply {
                        newBooks.forEach {
                            add(Book(it.title, it.subtitle, it.isbn13, "", "", "", "", timestamp))
                        }
                    }
                }

                insert(*books.toTypedArray())

                getLibraryResponsesByDate().find {
                    formatter.format(it.books.first().timestamp).equals("1980-06-20")
                }.also { response ->
                    assertThat(response).isNotNull()
                    if (response != null) {
                        assertThat(response.books.map { it.isbn13 }).isEqualTo(books.map { it.isbn13 })
                        delete(*response.books.toTypedArray())
                    }
                }
            }
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object FakeRepositoryModule {
        @Provides
        fun provideLibraryService() = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryService::class.java)

        @Provides
        fun provideLibraryDao(@ApplicationContext ctx: Context) =
            Room.databaseBuilder(ctx, LibraryDatabase::class.java, "Library")
                .build()
                .getLibraryDao()

        @Provides
        fun provideLibraryRepository(
            libraryService: LibraryService,
            libraryDao: LibraryDao
        ): LibraryRepository {
            return LibraryRepository(libraryService, libraryDao)
        }
    }
}