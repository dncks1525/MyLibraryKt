package com.chani.mylibrarykt.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookstoreAdapter
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityMainBinding

    private val bookstoreViewModel: BookstoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Firebase.analytics

        initQuickSearch()
        initBookstore()
    }

    override fun onResume() {
        super.onResume()
        if (binding.logoImg.isGone) {
            updateRecentCoverImg()
        }
    }

    private fun initQuickSearch() = with(binding) {
        val onClick =  View.OnClickListener {
            Intent(this@MainActivity, BookSearchActivity::class.java).apply {
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
            logoGroup.visibility = View.GONE
            mainGroup.visibility = View.VISIBLE
            updateRecentCoverImg()
        }

        val subjectCategories = mutableListOf<String>().apply {
            add("New Releases")
            add("Android")
            add("Kotlin")
            add("IOS")
            add("Rust")
            add("Python")
        }
        subjectCategories.forEach { book ->
            val bookChip = Chip(this@MainActivity).apply {
                text = book
                setOnClickListener { v ->
                    when (v) {
                        is Chip -> {
                            val idx = subjectCategories.indexOf(v.text)
                            bookstoreRecycler.smoothScrollToPosition(idx)
                        }
                    }
                }
            }
            subjectCategoryChips.addView(bookChip)
        }

        bookstoreRecycler.setHasFixedSize(true)
        bookstoreRecycler.adapter = BookstoreAdapter(subjectCategories, bookstoreViewModel)
    }

    private fun updateRecentCoverImg() = lifecycleScope.launch(Dispatchers.IO) {
//        historyDao.getLastHistory()?.let { user ->
//            if (File(user.coverImgPath).exists()) {
//                val bmp = BitmapFactory.decodeFile(user.coverImgPath)
//                lifecycleScope.launch {
//                    binding.recentHistoryImgbtn.apply {
//                        if (drawable is BitmapDrawable) {
//                            (drawable as BitmapDrawable).bitmap.recycle()
//                        }
//
//                        setImageDrawable(BitmapDrawable(resources, bmp))
//
//                        if (isGone) visibility = View.VISIBLE
//                        if (!hasOnClickListeners()) {
//                            setOnClickListener {
//                                AppLog.d("recentHistoryImgbtn clicked")
//                                Intent(this@MainActivity, RecentHistoryActivity::class.java).apply {
//                                    startActivity(this)
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                lifecycleScope.launch {
//                    if (binding.recentHistoryImgbtn.isVisible) {
//                        binding.recentHistoryImgbtn.visibility = View.GONE
//                    }
//                }
//            }
//        }
    }
}