package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.data.entity.Book
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val bookstoreViewModel: BookstoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            newRecycler.setHasFixedSize(true)
            newRecycler.adapter = BookstoreAdapter()
            lifecycleScope.launch {
                bookstoreViewModel.getNewBooks().collectLatest {
                    (newRecycler.adapter as BookstoreAdapter).submitData(it)
                }
            }
        }
    }
}