package com.chani.mylibrarykt.data.remote.model

data class Books(
    val error: Int,
    val total: Int,
    val page: Int,
    val books: List<Book>,
)