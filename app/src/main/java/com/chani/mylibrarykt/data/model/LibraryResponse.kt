package com.chani.mylibrarykt.data.model

data class LibraryResponse(
    val books: List<Book>,
    val error: Int = 0,
    val total: Int = 0,
    val page: Int = 0,
)