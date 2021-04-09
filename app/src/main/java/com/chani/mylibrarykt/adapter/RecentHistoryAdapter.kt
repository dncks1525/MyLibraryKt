package com.chani.mylibrarykt.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.databinding.ItemHistoryBinding
import com.chani.mylibrarykt.util.AppLog
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class RecentHistoryAdapter :
    PagingDataAdapter<History, RecentHistoryAdapter.RecentHistoryHolder>(RecentHistoryComparator()) {
    private lateinit var recycler: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recycler = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentHistoryHolder {
        return RecentHistoryHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecentHistoryHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RecentHistoryHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) = with(binding) {
            val format = SimpleDateFormat("MM-dd", Locale.getDefault())
            val time = format.format(Date(history.timestamp))
            AppLog.d("time = $time")
            AppLog.d("history = $history")
        }
    }

    class RecentHistoryComparator : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.isbn13 == newItem.isbn13
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.book == newItem.book
        }
    }
}