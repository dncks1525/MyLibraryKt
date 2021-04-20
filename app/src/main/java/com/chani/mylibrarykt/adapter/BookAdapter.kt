package com.chani.mylibrarykt.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.R
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
            val titleTxt: TextView
            val subtitleTxt: TextView?
            val priceTxt: TextView
            val coverImg: ImageView
            val root: ViewGroup

            when (binding) {
                is ItemBookBinding -> {
                    titleTxt = binding.titleTxt
                    subtitleTxt = null
                    priceTxt = binding.priceTxt
                    coverImg = binding.coverImg
                    root = binding.root
                }
                is ItemBookListBinding -> {
                    titleTxt = binding.titleTxt
                    subtitleTxt = binding.subtitleTxt
                    priceTxt = binding.priceTxt
                    coverImg = binding.coverImg
                    root = binding.root
                }
                else -> return
            }

            titleTxt.text = book.title
            subtitleTxt?.text = book.subtitle
            priceTxt.text = book.price
            Glide.with(binding.root)
                .load(book.image)
                .placeholder(R.drawable.book_placeholder)
                .centerCrop()
                .thumbnail(0.3f)
                .into(coverImg)

            root.setOnClickListener {
                val ctx = root.context
                Intent(ctx, BookDetailActivity::class.java).apply {
                    putExtra(AppConst.EXTRA_ISBN, book.isbn13)
                    putExtra(AppConst.EXTRA_COVER, coverImg.toByteArray())
                    putExtra(AppConst.EXTRA_NO_HISTORY, isNoHistory)
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        (root.context as Activity),
                        coverImg,
                        coverImg.transitionName
                    )
                    ctx.startActivity(this, options.toBundle())
                }
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