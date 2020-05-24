package com.chani.mylibrarykt

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.chani.mylibrarykt.adapters.PageAdapter
import com.chani.mylibrarykt.data.BookStoreApi.Book
import com.chani.mylibrarykt.data.BookStoreApi.BookDetail
import com.chani.mylibrarykt.data.BookModel
import com.chani.mylibrarykt.data.PageItem.PageType
import com.chani.mylibrarykt.Pages.*
import com.chani.mylibrarykt.data.PageItem
import com.chani.mylibrarykt.utils.AppLog
import com.chani.mylibrarykt.data.BookStoreApi
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    private lateinit var loadingDialog: AlertDialog
    private lateinit var searchView: SearchView
    private val searchSuggestions by lazy {
        SearchRecentSuggestions(this@MainActivity, SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE)
    }
    private var isSoftKeyShowing = false
    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private val bookRepo by lazy { BookStoreApi(this).bookRepo }
    private lateinit var pages: List<PageItem>
    private lateinit var query: String
    private var searchPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        createDialog()
        job = SupervisorJob()

        val rootView = window.decorView.rootView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val diff = rootView.rootView.height - rootView.height
            isSoftKeyShowing = diff > 100
        }

        pages = listOf(
            registerPage(PageType.NEW),
            registerPage(PageType.SEARCH),
            registerPage(PageType.BOOKMARK),
            registerPage(PageType.HISTORY)
        )
        pager.adapter = PageAdapter(pages)
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                hideSoftKeyboard()
                if (::searchView.isInitialized) {
                    searchView.clearFocus()
                }
            }
        })
        TabLayoutMediator(tab, pager) { tab, position ->
            tab.text = pages[position].type.name
        }.attach()

        requestNew()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                if (::searchView.isInitialized) {
                    searchView.isIconified = false
                    searchView.setQuery(query, true)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.appbar_action, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = menu.findItem(R.id.searchItem).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    AppLog.d("query = $it")
                    searchView.clearFocus()
                    searchSuggestions.saveRecentQuery(it, null)
                    if (pager.currentItem != PageType.SEARCH.ordinal) {
                        pager.setCurrentItem(PageType.SEARCH.ordinal, true)
                    }

                    searchPage = 0
                    this@MainActivity.query = it
                    pages[PageType.SEARCH.ordinal].bookModel.removeAll()
                    requestSearch(it, searchPage)

                    return true
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val request = when (item.itemId) {
            R.id.sortPriceDsc -> PageAdapter.REQUEST_SORT_BY_PRICE_DSC
            R.id.sortPriceAsc -> PageAdapter.REQUEST_SORT_BY_PRICE_ASC
            R.id.sortTitleDsc -> PageAdapter.REQUEST_SORT_BY_TITLE_DSC
            R.id.sortTitleAsc -> PageAdapter.REQUEST_SORT_BY_TITLE_ASC
            else -> -1
        }

        if (request > 0) {
            (pager.adapter as PageAdapter).request(pager.currentItem, request)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityReenter(resultCode: Int, data: Intent) {
        super.onActivityReenter(resultCode, data)
        if (data.hasExtra(MyLibraryConst.KEY_ISBN13)) {
            val isbn13 = data.getStringExtra(MyLibraryConst.KEY_ISBN13)
            val isBookmarked = data.getBooleanExtra(MyLibraryConst.KEY_BOOKMARK, false)
            launch {
                pages[pager.currentItem].bookModel.data.value?.let { books ->
                    books.find { it.isbn13 == isbn13 }?.let {
                        it.isBookmarked = isBookmarked

                        val bookmarkBookModel = pages[PageType.BOOKMARK.ordinal].bookModel
                        val bookmark = bookmarkBookModel.data.value
                        if (isBookmarked) {
                            if (bookmark == null || it !in bookmark) {
                                withContext(Dispatchers.Main) { bookmarkBookModel.add(it) }
                            }
                        } else {
                            if (bookmark != null && it in bookmark) {
                                withContext(Dispatchers.Main) { bookmarkBookModel.remove(it) }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchSuggestions.clearHistory()
        loadingDialog.dismiss()
        job.cancel()
    }

    private fun hideSoftKeyboard() {
        if (isSoftKeyShowing) {
            inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
        }
    }

    private fun createDialog() {
        loadingDialog = AlertDialog.Builder(this).apply {
            setCancelable(true)
            setView(R.layout.dialog_loading)
        }.create()
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun registerPage(type: PageType) = when (type) {
        PageType.NEW -> {
            NewPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.data.observe(this@MainActivity, Observer {
                    AppLog.d("NewPage size = ${it.size}")
                    pager.adapter?.notifyItemChanged(PageType.NEW.ordinal)
                })
                onItemClick = { view, book ->
                    requestDetail(view, book)
                }
            }
        }
        PageType.SEARCH -> {
            SearchPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.data.observe(this@MainActivity, Observer {
                    AppLog.d("SearchPage size = ${it.size}")
                    if (searchPage > 0) {
                        pager.adapter?.let { p ->
                            val adapter = p as PageAdapter
                            adapter.request(PageType.SEARCH.ordinal, PageAdapter.REQUEST_LOAD_MORE)
                        }
                    } else {
                        pager.adapter?.notifyItemChanged(PageType.SEARCH.ordinal)
                    }
                })
                onItemClick = { view, book ->
                    requestDetail(view, book)
                }
                onScroll = {
                    if (searchPage >= 0) {
                        requestSearch(query, ++searchPage)
                    }
                }
            }
        }
        PageType.BOOKMARK -> {
            BookmarkPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.data.observe(this@MainActivity, Observer {
                    AppLog.d("BookmarkPage size = ${it.size}")
                    pager.adapter?.notifyItemChanged(PageType.BOOKMARK.ordinal)
                })
                onItemClick = { view, book ->
                    requestDetail(view, book)
                }
            }
        }
        PageType.HISTORY -> {
            HistoryPage().apply {
                bookModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(BookModel::class.java)
                bookModel.data.observe(this@MainActivity, Observer {
                    AppLog.d("HistoryPage size = ${it.size}")
                    pager.adapter?.notifyItemChanged(PageType.HISTORY.ordinal)
                })
                onItemClick = { view, book ->
                    requestDetail(view, book)
                }
            }
        }
    }

    private fun requestNew(): Job = launch(Dispatchers.IO) {
        val books = bookRepo.getNewBook().books
        if (books.size > 0) {
            withContext(Dispatchers.Main) {
                pages[PageType.NEW.ordinal].bookModel.addAll(books)
            }
        }
    }

    private fun requestSearch(query: String, page: Int): Job = launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) { if (!loadingDialog.isShowing) loadingDialog.show() }
        val books = bookRepo.search(query, page).books
        withContext(Dispatchers.Main) { if (loadingDialog.isShowing) loadingDialog.dismiss() }

        if (books.size > 0) {
            withContext(Dispatchers.Main) {
                pages[PageType.SEARCH.ordinal].bookModel.addAll(books)
            }
        } else {
            searchPage = -1
        }
    }

    private fun requestDetail(view: View, book: Book): Job = launch(Dispatchers.IO) {
        val historyBookModel = pages[PageType.HISTORY.ordinal].bookModel
        val history = historyBookModel.data.value
        if (history == null) {
            withContext(Dispatchers.Main) { historyBookModel.add(book) }
        } else {
            history.find { it.isbn13 == book.isbn13 }.also { b ->
                if (b == null) {
                    withContext(Dispatchers.Main) { historyBookModel.add(book) }
                }
            }
        }

        val bookDetail = bookRepo.getBookDetail(book.isbn13)
        withContext(Dispatchers.Main) { startBookDetailActivity(view, bookDetail, book.isBookmarked) }
    }

    private fun startBookDetailActivity(view: View, bookDetail: BookDetail, isBookmarked: Boolean) {
        val scene = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, view, view.transitionName)
        val intent = Intent(this@MainActivity, BookDetailActivity::class.java)
        intent.putExtra(MyLibraryConst.KEY_BOOK_DETAIL, bookDetail)
        intent.putExtra(MyLibraryConst.KEY_BOOKMARK, isBookmarked)
        startActivity(intent, scene.toBundle())
    }

}