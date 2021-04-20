package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.BookListType
import com.chani.mylibrarykt.data.model.LibraryResponse
import com.chani.mylibrarykt.databinding.ItemRecentBooksBinding
import com.chani.mylibrarykt.viewmodel.RecentBooksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RecentBooksAdapter(
    private val recentBooksViewModel: RecentBooksViewModel
) : PagingDataAdapter<LibraryResponse, RecentBooksAdapter.RecentBooksHolder>(RecentBooksComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentBooksHolder {
        return RecentBooksHolder(
            ItemRecentBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecentBooksHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RecentBooksHolder(
        private val binding: ItemRecentBooksBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(libraryResponse: LibraryResponse) = with(binding) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = formatter.format(libraryResponse.books.first().timestamp)
            dateTxt.text = date

            val adapter = BookAdapter(BookListType.LIST_WITH_NO_HISTORY).apply {
                CoroutineScope(Dispatchers.Main).launch {
                    loadStateFlow.collectLatest { state ->
                        if (state.refresh == LoadState.Loading) {
                            loadingProgress.visibility = View.VISIBLE
                        } else {
                            if (loadingProgress.isVisible) {
                                loadingProgress.visibility = View.GONE
                            }
                        }
                    }
                }
            }

            historyRecycler.adapter = adapter
            CoroutineScope(Dispatchers.IO).launch {
                recentBooksViewModel.flowFrom(libraryResponse.books).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private class RecentBooksComparator : DiffUtil.ItemCallback<LibraryResponse>() {
        override fun areContentsTheSame(oldItem: LibraryResponse, newItem: LibraryResponse): Boolean {
            return oldItem.books.first().isbn13 == newItem.books.first().isbn13
        }

        override fun areItemsTheSame(oldItem: LibraryResponse, newItem: LibraryResponse): Boolean {
            return oldItem == newItem
        }
    }
}