package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.data.entity.Book
import com.chani.mylibrarykt.databinding.ItemBookSimpleBinding

class BookstoreAdapter : PagingDataAdapter<Book, BookstoreAdapter.RecyclerViewHolder>(RecyclerDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            ItemBookSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RecyclerViewHolder(
        private val binding: ItemBookSimpleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) = with(binding) {
            data?.let {
                Glide.with(binding.root)
                    .load(it.image)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .into(coverImg)
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