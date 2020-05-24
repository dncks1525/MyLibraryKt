package com.chani.mylibrarykt.data

import android.view.View
import com.chani.mylibrarykt.data.BookStoreApi.Book

open class PageItem(val type: PageType) {
    lateinit var bookModel: BookModel
    var onItemClick: ((view: View, book: Book) -> Unit)? = null
    var onScroll: (() -> Unit)? = null

    enum class PageType { NEW, SEARCH, BOOKMARK, HISTORY }
}