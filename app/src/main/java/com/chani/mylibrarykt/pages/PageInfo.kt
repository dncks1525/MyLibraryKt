package com.chani.mylibrarykt.pages

import android.view.View
import com.chani.mylibrarykt.BookModel
import com.chani.mylibrarykt.BookStoreApi
import com.chani.mylibrarykt.BookStoreApi.Book

open class PageInfo(protected val name: String, protected val type: PageType) {
    var books: BookModel? = null
    var onItemClick: ((view: View, book: Book) -> Unit)? = null
    var onScroll: (() -> Unit)? = null

    enum class PageType { NEW, SEARCH, BOOKMARK, HISTORY }
}