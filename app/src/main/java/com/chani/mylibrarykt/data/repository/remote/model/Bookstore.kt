package com.chani.mylibrarykt.data.repository.remote.model

data class Bookstore(
    val error: Int,
    val total: Int,
    val page: Int,
    val books: List<Book>,
)