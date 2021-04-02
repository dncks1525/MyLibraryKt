package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookAdapter
import com.chani.mylibrarykt.data.remote.viewmodel.BookstoreViewModel
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val bookstoreViewModel: BookstoreViewModel by viewModels()
    @Inject lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        mutableListOf<String>().apply {
            add("Android")
            add("Kotlin")
            add("IOS")
            add("C#")
            add("Python")
        }.forEach { book ->
            val bookChip = Chip(this).apply {
                text = book
                setOnClickListener { v ->
                    when (v) {
                        is Chip -> println(v.text)
                    }
                }
            }
            binding.chipGroup.addView(bookChip)
        }

        binding.apply {
            bookstoreRecycler.setHasFixedSize(true)
            bookstoreRecycler.adapter = bookAdapter
            lifecycleScope.launch {
                bookstoreViewModel.getNewBooks().collectLatest {
                    bookAdapter.submitData(it)
                }
            }
        }
    }
}