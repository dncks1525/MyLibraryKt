package com.chani.mylibrarykt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chani.mylibrarykt.PageItem.PageType
import com.chani.mylibrarykt.Pages.*
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    val bookRepo by lazy { BookStoreApi(this).bookRepo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val pages = listOf(
            registerPage(PageType.NEW),
            registerPage(PageType.SEARCH),
            registerPage(PageType.BOOKMARK),
            registerPage(PageType.HISTORY)
        )

        GlobalScope.launch(Dispatchers.Main) {
            pages[0].bookModel.books.value = bookRepo.getNewBook().books
            pager.adapter = PageAdapter(pages)
        }
    }

    private fun registerPage(type: PageType) = when (type) {
        PageType.NEW -> {
            NewPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.books.observe(this@MainActivity, Observer {
                    println("size = ${it.size}")
                })
                onItemClick = { view, book ->

                }
            }
        }
        PageType.SEARCH -> {
            SearchPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.books.observe(this@MainActivity, Observer {

                })
                onItemClick = { view, book ->

                }
                onScroll = {

                }
            }
        }
        PageType.BOOKMARK -> {
            BookmarkPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.books.observe(this@MainActivity, Observer {

                })
                onItemClick = { view, book ->

                }
            }
        }
        PageType.HISTORY -> {
            HistoryPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.books.observe(this@MainActivity, Observer {

                })
                onItemClick = { view, book ->

                }
            }
        }
    }
}