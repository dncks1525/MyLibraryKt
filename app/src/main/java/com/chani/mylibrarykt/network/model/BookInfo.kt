package com.chani.mylibrarykt.network.model

data class BookInfo(
    val error: String,
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val language: String,
    val isbn10: String,
    val isbn13: String,
    val pages: String,
    val year: String,
    val rating: String,
    val desc: String,
    val price: String,
    val image: String,
    val url: String,
    val pdf: Pdf
)

data class Pdf(
    val Chapter2: String,
    val Chapter5: String
)