package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.databinding.ItemRecentBooksBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.RecentBooksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RecentBooksAdapter(
    private val recentBooksViewModel: RecentBooksViewModel
) : PagingDataAdapter<List<History>, RecentBooksAdapter.RecentBooksHolder>(RecentBooksComparator()) {
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
        fun bind(histories: List<History>) = with(binding) {
            AppLog.d("RecentBookHolder histories = ${histories.count()}, ${histories.first().title}")

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = formatter.format(histories.first().timestamp)
            dateTxt.text = date

            val adapter = HistoryAdapter()
            historyRecycler.adapter = adapter
            CoroutineScope(Dispatchers.IO).launch {
                recentBooksViewModel.transformHistories(histories).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    private class RecentBooksComparator : DiffUtil.ItemCallback<List<History>>() {
        override fun areContentsTheSame(oldItem: List<History>, newItem: List<History>): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: List<History>, newItem: List<History>): Boolean {
            return oldItem.first().isbn13 == newItem.first().isbn13
        }
    }
}