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
import com.chani.mylibrarykt.viewmodel.RecentHistoryViewModel
import com.chani.mylibrarykt.viewmodel.factory.RecentHistoryViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecentHistoryActivity : AppCompatActivity() {
    private val binding: ActivityRecentHistoryBinding by lazy { ActivityRecentHistoryBinding.inflate(layoutInflater) }

    @Inject lateinit var historyDao: HistoryDao
    private lateinit var historyRepository: HistoryRepository
    private val recentHistoryViewModel: RecentHistoryViewModel by viewModels {
        RecentHistoryViewModelFactory(historyRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val subjectContent = ContentSubjectBinding.bind(binding.root)
        subjectContent.backImgbtn.setOnClickListener {
            finish()
//            supportFinishAfterTransition()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val histories = historyDao.getHistories()
            historyRepository = HistoryRepository(histories)

            lifecycleScope.launch {
                val recentHistoryAdapter = RecentHistoryAdapter(histories, recentHistoryViewModel)
                binding.recentHistoryRecycler.apply {
                    setHasFixedSize(true)
                    adapter = recentHistoryAdapter
                }
            }
        }
    }
}