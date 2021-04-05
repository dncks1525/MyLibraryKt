package com.chani.mylibrarykt.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.remote.model.Book
import com.chani.mylibrarykt.databinding.ItemBookBinding
import com.chani.mylibrarykt.ui.BookDetailActivity
import java.io.ByteArrayOutputStream

class BookAdapter : PagingDataAdapter<Book, BookAdapter.BookHolder>(BooksComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class BookHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book?) = with(binding) {
            data?.let { book ->
                titleTxt.text = book.title
                priceTxt.text = book.price
                Glide.with(binding.root)
                    .load(book.image)
                    .placeholder(R.drawable.book_placeholder)
                    .centerCrop()
                    .thumbnail(0.3f)
                    .into(coverImg)

                root.setOnClickListener {
                    val ctx = root.context
                    with(Intent(ctx, BookDetailActivity::class.java)) {
                        putExtra(AppConst.EXTRA_ISBN, book.isbn13)
                        putExtra(AppConst.EXTRA_COVER, toByteArray(coverImg))
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

        private fun toByteArray(img: ImageView): ByteArray {
            val bmp = (img.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
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