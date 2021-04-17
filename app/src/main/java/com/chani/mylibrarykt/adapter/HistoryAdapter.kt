package com.chani.mylibrarykt.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.data.local.History
import com.chani.mylibrarykt.databinding.ItemHistoryBinding
import com.chani.mylibrarykt.toByteArray
import com.chani.mylibrarykt.ui.BookDetailActivity
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

            root.setOnClickListener {
                Intent(root.context, BookDetailActivity::class.java).apply {
                    putExtra(AppConst.EXTRA_ISBN, history.isbn13)
                    putExtra(AppConst.EXTRA_COVER, coverImg.toByteArray())
                    putExtra(AppConst.EXTRA_NO_HISTORY, true)
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        (root.context as Activity),
                        coverImg,
                        coverImg.transitionName
                    )
                    root.context.startActivity(this, options.toBundle())
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