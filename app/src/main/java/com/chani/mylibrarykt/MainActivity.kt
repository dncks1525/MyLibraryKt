package com.chani.mylibrarykt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chani.mylibrarykt.PageItem.PageType
import com.chani.mylibrarykt.Pages.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val pageAdapter = PageAdapter(
            listOf(
                registerPage(PageType.NEW),
                registerPage(PageType.SEARCH),
                registerPage(PageType.BOOKMARK),
                registerPage(PageType.HISTORY)
            )
        )

        GlobalScope.launch {

        }
    }

    private fun registerPage(type: PageType) = when (type) {
        PageType.NEW -> {
            NewPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.books.observe(this@MainActivity, Observer {

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