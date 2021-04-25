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

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.adapter.RecentBooksAdapter
import com.chani.mylibrarykt.databinding.ActivityRecentBooksBinding
import com.chani.mylibrarykt.databinding.ContentSubjectBinding
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

        with(binding) {
            with(ContentSubjectBinding.bind(root)) {
                titleTxt.text = getString(R.string.common_recent_books)
                backImgbtn.setOnClickListener { finish() }
            }

            val recentBooksAdapter = RecentBooksAdapter(recentBooksViewModel)
            recentBooksRecycler.adapter = recentBooksAdapter
            recentBooksRecycler.setHasFixedSize(true)

            lifecycleScope.launch(Dispatchers.IO) {
                recentBooksViewModel.getLibraryResponsesByDate().collectLatest {
                    recentBooksAdapter.submitData(it)
                }
            }
        }
    }
}