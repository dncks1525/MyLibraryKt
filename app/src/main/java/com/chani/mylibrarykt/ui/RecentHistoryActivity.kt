package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.RecentHistoryAdapter
import com.chani.mylibrarykt.data.repository.HistoryRepository
import com.chani.mylibrarykt.data.repository.local.dao.HistoryDao
import com.chani.mylibrarykt.databinding.ActivityRecentHistoryBinding
import com.chani.mylibrarykt.databinding.ContentSubjectBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.RecentHistoryViewModel
import com.chani.mylibrarykt.viewmodel.factory.RecentHistoryViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecentHistoryActivity : AppCompatActivity() {
    private val binding: ActivityRecentHistoryBinding by lazy { ActivityRecentHistoryBinding.inflate(layoutInflater) }

    private val recentHistoryViewModel: RecentHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val subjectContent = ContentSubjectBinding.bind(binding.root)
        subjectContent.backImgbtn.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            val recentHistoryAdapter = RecentHistoryAdapter()
            binding.recentHistoryRecycler.adapter = recentHistoryAdapter
            binding.recentHistoryRecycler.setHasFixedSize(true)

            recentHistoryViewModel.getHistories().collect {
                recentHistoryAdapter.submitData(it)
            }
        }
    }
}