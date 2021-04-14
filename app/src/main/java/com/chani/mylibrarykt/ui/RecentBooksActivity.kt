package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.data.local.HistoryDao
import com.chani.mylibrarykt.databinding.ActivityRecentBooksBinding
import com.chani.mylibrarykt.databinding.ContentSubjectBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.RecentBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecentBooksActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityRecentBooksBinding
    private val recentBooksViewModel: RecentBooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val subjectBinding = ContentSubjectBinding.bind(binding.root)
        subjectBinding.backImgbtn.setOnClickListener {
            finish()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            recentBooksViewModel.getRecentBooks().collectLatest {

            }
        }
    }
}