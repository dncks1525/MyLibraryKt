package com.chani.mylibrarykt.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "dncks"

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val bookstoreViewModel: BookstoreViewModel by viewModels()
    @Inject lateinit var searchRecentSuggestions: SearchRecentSuggestions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        initQuickSearch()
        initBookstore()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                with(binding) {
                    quickSearch.setQuery(query, true)
                    quickSearch.clearFocus()
                }
            }
        }
    }

    private fun initQuickSearch() = with(binding) {
        searchRecentSuggestions.clearHistory()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        quickSearch.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        quickSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    Log.d(TAG, "onQueryTextSubmit: $it")
                    searchRecentSuggestions.saveRecentQuery(it, null)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        (quickSearch.findViewById(androidx.appcompat.R.id.search_close_btn) as View?)
            ?.setOnClickListener {
                quickSearch.setQuery("", false)
                quickSearch.clearFocus()
            }
    }

    private fun initBookstore() = with(binding) {
        val dataList = mutableListOf<String>().apply {
            add("New Releases")
            add("Android")
            add("Kotlin")
            add("IOS")
            add("Rust")
            add("Python")
        }

        dataList.forEach { book ->
            val bookChip = Chip(this@MainActivity).apply {
                text = book
                setOnClickListener { v ->
                    when (v) {
                        is Chip -> println(v.text)
                    }
                }
            }
            chipGroup.addView(bookChip)
        }

        bookstoreRecycler.setHasFixedSize(true)
        bookstoreRecycler.adapter = BookstoreAdapter(dataList, bookstoreViewModel)
    }
}