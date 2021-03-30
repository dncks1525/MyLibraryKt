package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.lang.Exception
import java.lang.IllegalArgumentException
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
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