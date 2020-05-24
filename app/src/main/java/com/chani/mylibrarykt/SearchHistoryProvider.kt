package com.chani.mylibrarykt

import android.content.SearchRecentSuggestionsProvider

class SearchHistoryProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.chani.mylibrarykt.SearchHistoryProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }
}