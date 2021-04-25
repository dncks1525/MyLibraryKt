/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chani.mylibrarykt.ui

import android.app.ActivityOptions
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
import com.chani.mylibrarykt.adapter.LibraryAdapter
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.viewmodel.LibraryViewModel
import com.google.android.material.chip.Chip
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityMainBinding
    @Inject lateinit var libraryRepository: LibraryRepository
    private val libraryViewModel: LibraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Firebase.analytics

        initQuickSearch()
        initLibrary()
    }

    override fun onResume() {
        super.onResume()
        if (!binding.logoImg.isVisible) {
            updateRecentBook()
        }
    }

    private fun initQuickSearch() = with(binding) {
        val onClick = View.OnClickListener {
            Intent(this@MainActivity, BookSearchActivity::class.java).apply {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity,
                    quickSearch,
                    quickSearch.transitionName
                )
                startActivity(this, options.toBundle())
            }
        }

        quickSearch.apply {
            findViewById<EditText>(androidx.appcompat.R.id.search_src_text).apply {
                isFocusable = true
                isFocusableInTouchMode = false
                setOnClickListener(onClick)
            }

            setOnClickListener(onClick)
        }
    }

    private fun initLibrary() = with(binding) {
        lifecycleScope.launch {
            delay(2000)
            logoGroup.visibility = View.GONE
            mainGroup.visibility = View.VISIBLE
            updateRecentBook()
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
                            libraryRecycler.smoothScrollToPosition(idx)
                        }
                    }
                }
            }
            subjectCategoryChips.addView(bookChip)
        }

        libraryRecycler.setHasFixedSize(true)
        libraryRecycler.adapter = LibraryAdapter(subjectCategories, libraryViewModel)
    }

    private fun updateRecentBook() = lifecycleScope.launch(Dispatchers.IO) {
        libraryRepository.getBookLast()?.let { book ->
            withContext(Dispatchers.Main) {
                book.localImgPath?.let { localImgPath ->
                    if (File(localImgPath).exists()) {
                        binding.recentBooksImgbtn.apply {
                            if (drawable != null && drawable is BitmapDrawable) {
                                (drawable as BitmapDrawable).bitmap.recycle()
                            }
                            setImageBitmap(BitmapFactory.decodeFile(localImgPath))

                            if (isGone) visibility = View.VISIBLE
                            if (!hasOnClickListeners()) {
                                setOnClickListener {
                                    Intent(this@MainActivity, RecentBooksActivity::class.java).apply {
                                        startActivity(this)
                                    }
                                }
                            }
                        }
                    } else {
                        if (binding.recentBooksImgbtn.isVisible) {
                            binding.recentBooksImgbtn.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}