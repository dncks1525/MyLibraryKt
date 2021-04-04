package com.chani.mylibrarykt.provider

import android.content.SearchRecentSuggestionsProvider

class QuickSearchProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTH, MODE)
    }

    companion object {
        const val AUTH = "com.chani.mylibrarykt.provider.QuickSearchProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }
}