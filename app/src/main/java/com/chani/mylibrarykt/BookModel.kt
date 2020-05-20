package com.chani.mylibrarykt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chani.mylibrarykt.BookStoreApi.Book

class BookModel : ViewModel() {
    val books = MutableLiveData<MutableList<Book>>()
}