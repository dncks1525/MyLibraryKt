package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.databinding.ItemHistoryBinding
import com.chani.mylibrarykt.util.AppLog

class RecentHistoryAdapter :
    PagingDataAdapter<List<History>, RecentHistoryAdapter.RecentHistoryHolder>(RecentHistoryComparator()) {
    private lateinit var currRecycler: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        currRecycler = recyclerView
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
        fun bind(histories: List<History>) = with(binding) {
            AppLog.d("$histories")
        }
    }

    class RecentHistoryComparator : DiffUtil.ItemCallback<List<History>>() {
        override fun areItemsTheSame(oldItem: List<History>, newItem: List<History>): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: List<History>, newItem: List<History>): Boolean {
            return oldItem == newItem
        }
    }
}