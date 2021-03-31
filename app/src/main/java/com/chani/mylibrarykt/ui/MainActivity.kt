package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.data.viewmodel.BookstoreViewModel
import com.chani.mylibrarykt.databinding.ActivityMainBinding
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

    @Inject lateinit var bookstoreAdapter: BookstoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        binding.apply {
            newRecycler.setHasFixedSize(true)
            newRecycler.adapter = bookstoreAdapter
            lifecycleScope.launch {
                bookstoreViewModel.getNewBooks().collectLatest {
                    bookstoreAdapter.submitData(it)
                }
            }
        }
    }
}