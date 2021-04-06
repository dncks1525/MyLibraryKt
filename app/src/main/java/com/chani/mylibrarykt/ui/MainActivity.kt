package com.chani.mylibrarykt.ui

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        with(quickSearch) {
//            searchRecentSuggestions.clearHistory()
//
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    if (query != null) {
//                        AppLog.d(query)
//                        searchRecentSuggestions.saveRecentQuery(query, null)
//                    }
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return true
//                }
//            })
//
//            findViewById<View>(androidx.appcompat.R.id.search_close_btn)?.setOnClickListener {
//                setQuery("", false)
//                clearFocus()
//            }
//        }

        with(quickSearch) {
            val onClick = {v: View? -> {
                v?.setOnClickListener {
                    Intent(this@MainActivity, SearchActivity::class.java).apply {
                        val options = ActivityOptions.makeSceneTransitionAnimation(
                            this@MainActivity, quickSearch,
                            quickSearch.transitionName
                        )
                        startActivity(this, options.toBundle())
                    }
                }
            }}

//            findViewById<EditText>(androidx.appcompat.R.id.search_edit_frame)?.setOnClickListener(onClick)
        }

        quickSearch.setOnClickListener {
            Intent(this@MainActivity, SearchActivity::class.java).apply {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity, quickSearch,
                    quickSearch.transitionName
                )
                startActivity(this, options.toBundle())
            }
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