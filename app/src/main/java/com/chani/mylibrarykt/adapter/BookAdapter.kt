package com.chani.mylibrarykt.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.data.BookListType
import com.chani.mylibrarykt.data.model.Book
import com.chani.mylibrarykt.databinding.ItemBookBinding
import com.chani.mylibrarykt.databinding.ItemBookListBinding
import com.chani.mylibrarykt.toByteArray
import com.chani.mylibrarykt.ui.BookDetailActivity

class BookAdapter(
    private val listType: BookListType = BookListType.FRAME
) : PagingDataAdapter<Book, BookAdapter.BookHolder>(BooksComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return when(listType) {
            BookListType.FRAME -> BookHolder(
                ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            BookListType.LIST -> BookHolder(
                ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            BookListType.LIST_WITH_NO_HISTORY -> BookHolder(
                ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                true
            )
        }
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BookHolder(
        private val binding: ViewBinding,
        private val isNoHistory: Boolean = false,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            when (binding) {
                is ItemBookBinding -> {
                    binding.book = book
                    binding.root.setOnClickListener { onClickListener(book, binding.coverImg)}
                }
                is ItemBookListBinding -> {
                    binding.book = book
                    binding.root.setOnClickListener { onClickListener(book, binding.coverImg)}
                }
            }
        }

        private fun onClickListener(book: Book, coverImg: ImageView) {
            val context = binding.root.context
            Intent(context, BookDetailActivity::class.java).apply {
                putExtra(AppConst.EXTRA_ISBN, book.isbn13)
                putExtra(AppConst.EXTRA_COVER, coverImg.toByteArray())
                putExtra(AppConst.EXTRA_NO_HISTORY, isNoHistory)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    (context as Activity),
                    coverImg,
                    coverImg.transitionName
                )
                context.startActivity(this, options.toBundle())
            }
        }
    }

    private class BooksComparator : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}