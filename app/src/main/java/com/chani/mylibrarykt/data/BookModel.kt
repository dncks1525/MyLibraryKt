package com.chani.mylibrarykt.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chani.mylibrarykt.data.BookStoreApi.Book

class BookModel : ViewModel() {
    val data = MutableLiveData<MutableList<Book>>()

    fun add(book: Book) {
        val value = data.value ?: mutableListOf()
        value.add(book)
        data.value = value
    }

    fun addAll(books: List<Book>) {
        val value = data.value ?: mutableListOf()
        value.addAll(books)
        data.value = value
    }

    fun remove(book: Book) {
        data.value?.let {
            it.remove(book)
            data.value = it
        }
    }

    fun removeAll() {
        data.value?.let {
            it.clear()
            data.value = it
        }
    }
}