package com.chani.mylibrarykt.data.entity

data class Books(
    val error: Int,
    val total: Int,
    val page: Int,
    val books: List<Book>,
)