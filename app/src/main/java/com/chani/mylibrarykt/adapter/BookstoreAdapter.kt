package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chani.mylibrarykt.databinding.ItemBookBinding
import com.chani.mylibrarykt.data.entity.Book

class BookstoreAdapter : PagingDataAdapter<Book, BookstoreAdapter.RecyclerViewHolder>(RecyclerDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RecyclerViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) = with(binding) {
            data?.let {
                Glide.with(binding.root)
                    .load(it.image)
                    .thumbnail(0.1f)
                    .into(coverImg)
                titleTxt.text = it.title
                priceTxt.text = it.price
            }
        }
    }

    private class RecyclerDiffUtil : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}