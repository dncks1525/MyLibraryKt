package com.chani.mylibrarykt.data

data class Books(
    val error: String,
    val total: String,
    val page: String,
    val books: List<Book>,
)

data class Book(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
)