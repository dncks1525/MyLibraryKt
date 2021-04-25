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

package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.databinding.ItemBookFooterBinding

class BookFooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<BookFooterAdapter.BookFooterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BookFooterHolder {
        return BookFooterHolder(
            ItemBookFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookFooterHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class BookFooterHolder(
        private val binding: ItemBookFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) = with(binding) {
            loadingProgress.visibility = View.GONE
            retryBtn.visibility = View.GONE
            if (!retryBtn.hasOnClickListeners()) {
                retryBtn.setOnClickListener { retry() }
            }

            if (loadState is LoadState.Loading) {
                loadingProgress.visibility = View.VISIBLE
            } else if (loadState is LoadState.Error) {
                retryBtn.visibility = View.VISIBLE
            }
        }
    }
}