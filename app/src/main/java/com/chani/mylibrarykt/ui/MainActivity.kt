package com.chani.mylibrarykt.ui

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.data.local.dao.UserDao
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val bookstoreViewModel: BookstoreViewModel by viewModels()
    @Inject lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAnalytics = Firebase.analytics

        initQuickSearch()
        initBookstore()
    }

    override fun onResume() {
        super.onResume()
        if (binding.logoImg.isGone) {
            updateRecentBook()
        }
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
        lifecycleScope.launch {
            delay(2000)
            logoImg.visibility = View.GONE
            mainGroup.visibility = View.VISIBLE
            updateRecentBook()
        }

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

    private fun updateRecentBook() = lifecycleScope.launch(Dispatchers.IO) {
        userDao.getLast()?.let { user ->
            if (File(user.coverImgPath).exists()) {
                val bmp = BitmapFactory.decodeFile(user.coverImgPath)
                lifecycleScope.launch {
                    binding.recentBookImgBtn.setImageDrawable(BitmapDrawable(resources, bmp))
                    if (binding.recentBookImgBtn.isGone) {
                        binding.recentBookImgBtn.visibility = View.VISIBLE
                    }
                }
            } else {
                lifecycleScope.launch {
                    if (binding.recentBookImgBtn.isVisible) {
                        binding.recentBookImgBtn.visibility = View.GONE
                    }
                }
            }
        }
    }
}