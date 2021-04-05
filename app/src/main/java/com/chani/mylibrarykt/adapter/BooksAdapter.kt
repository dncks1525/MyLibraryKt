package com.chani.mylibrarykt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.databinding.ItemBooksBinding
import com.chani.mylibrarykt.ui.BookDetailActivity

class BooksAdapter : PagingDataAdapter<Book, BooksAdapter.BooksHolder>(BooksComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
        return BooksHolder(
            ItemBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BooksHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BooksHolder(
        private val binding: ItemBooksBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) = with(binding) {
            data?.let { book ->
                titleTxt.text = book.title
                priceTxt.text = book.price
                Glide.with(binding.root)
                    .load(book.image)
                    .placeholder(R.drawable.book_placeholder)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .into(coverImg)

                root.setOnClickListener {
                    val ctx = root.context
                    with(Intent(ctx, BookDetailActivity::class.java)) {
                        putExtra(AppConst.EXTRA_ISBN, book.isbn13)
                        ctx.startActivity(this)
                    }
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