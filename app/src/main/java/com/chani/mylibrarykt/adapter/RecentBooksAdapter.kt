package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.databinding.ItemBookListBinding

class RecentBooksAdapter : PagingDataAdapter<List<History>, RecentBooksAdapter.HistoryHolder>(HistoryComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class HistoryHolder(
        private val binding: ItemBookListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(histories: List<History>) {

        }
    }

    private class HistoryComparator : DiffUtil.ItemCallback<List<History>>() {
        override fun areContentsTheSame(oldItem: List<History>, newItem: List<History>): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: List<History>, newItem: List<History>): Boolean {
            return oldItem == newItem
        }
    }
}