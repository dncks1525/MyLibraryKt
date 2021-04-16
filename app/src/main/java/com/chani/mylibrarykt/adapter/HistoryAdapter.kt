package com.chani.mylibrarykt.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.databinding.ItemHistoryBinding
import com.chani.mylibrarykt.util.AppLog
import java.io.File

class HistoryAdapter : PagingDataAdapter<History, HistoryAdapter.HistoryHolder>(HistoryComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        return HistoryHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class HistoryHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) = with(binding) {
            titleTxt.text = history.title
            subtitleTxt.text = history.subtitle
            priceTxt.text = history.price

            if (File(history.imgPath).exists()) {
                BitmapFactory.decodeFile(history.imgPath).apply {
                    coverImg.setImageBitmap(this)
                }
            }
        }
    }

    private class HistoryComparator : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.isbn13 == newItem.isbn13
        }
    }
}