package com.chani.mylibrarykt.ui

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val bookstoreViewModel: BookstoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        initQuickSearch()
        initBookstore()
    }

    private fun initQuickSearch() = with(binding) {
        val onClick =  View.OnClickListener {
            Intent(this@MainActivity, SearchActivity::class.java).apply {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity, quickSearch,
                    quickSearch.transitionName
                )
                startActivity(this, options.toBundle())
            }
        }

        with(quickSearch) {
            findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
                isFocusable = true
                isFocusableInTouchMode = false
                setOnClickListener(onClick)
            }

            setOnClickListener(onClick)
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
                        is Chip -> {
                            val idx = dataList.indexOf(v.text)
                            bookstoreRecycler.smoothScrollToPosition(idx)
                        }
                    }
                }
            }
            chipGroup.addView(bookChip)
        }

        bookstoreRecycler.setHasFixedSize(true)
        bookstoreRecycler.adapter = BookstoreAdapter(dataList, bookstoreViewModel)
    }
}