package com.chani.mylibrarykt

import android.view.View
import com.chani.mylibrarykt.BookStoreApi.Book

open class PageItem(val type: PageType) {
    lateinit var bookModel: BookModel
    lateinit var onItemClick: ((view: View, book: Book) -> Unit)
    lateinit var onScroll: (() -> Unit)

    enum class PageType { NEW, SEARCH, BOOKMARK, HISTORY }
}