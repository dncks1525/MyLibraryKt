package com.chani.mylibrarykt

import android.content.Context
import androidx.room.Room
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.data.local.HistoryDao
import com.chani.mylibrarykt.data.local.HistoryDatabase
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.di.module.RepositoryModule
import com.google.common.truth.Truth.*
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

    @Inject lateinit var historyDao: HistoryDao
    @Inject lateinit var bookstoreApi: BookstoreApi
    private lateinit var history: History

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            bookstoreApi.getNewBooks().books.last().also { book ->
                val timestamp = Calendar.getInstance().timeInMillis
                history = book.convertTo(timestamp, "myTestImgPath")
            }
        }
    }

    @Test
    fun deleteTest() {
        runBlocking {
            historyDao.delete(history)
            val lastHistory = historyDao.getLastHistory()
            assertThat(lastHistory?.isbn13).isNotEqualTo(history.isbn13)
        }
    }

    @Test
    fun insertTest() {
        runBlocking {
            historyDao.insert(history)
            val lastHistory = historyDao.getLastHistory()
            assertThat(lastHistory?.isbn13).isEqualTo(history.isbn13)

            historyDao.delete(history)
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

            val histories = bookstoreApi.getNewBooks().books.take(3).map {
                it.convertTo(timestamp, "testImgPath")
            }
            historyDao.insert(histories)

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val recentBooks = historyDao.getRecentBooks().find {
                formatter.format(it.first().timestamp).equals("1980-06-20")
            }
            assertThat(recentBooks?.map { it.isbn13 }).isEqualTo(histories.map { it.isbn13 })

            if (recentBooks != null) {
                historyDao.delete(recentBooks)
            }
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestRepositoryModule {
        @Provides
        fun provideBookstoreApi() = Retrofit.Builder()
            .baseUrl("https://api.itbook.store/1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookstoreApi::class.java)

        @Provides
        fun provideHistoryDao(@ApplicationContext ctx: Context) =
            Room.databaseBuilder(ctx, HistoryDatabase::class.java, "History")
                .build()
                .getHistoryDao()
    }
}