package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chani.mylibrarykt.databinding.ActivityRecentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentHistoryActivity : AppCompatActivity() {
    private val binding: ActivityRecentHistoryBinding by lazy { ActivityRecentHistoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}