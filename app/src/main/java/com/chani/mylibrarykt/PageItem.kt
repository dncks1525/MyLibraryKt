package com.chani.mylibrarykt

import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.chani.mylibrarykt.BookStoreApi.Book

open class PageItem(val type: PageType) {
    lateinit var bookModel: BookModel
    var onItemClick: ((view: View, book: Book) -> Unit)? = null
    var onScroll: (() -> Unit)? = null

    enum class PageType { NEW, SEARCH, BOOKMARK, HISTORY }
}