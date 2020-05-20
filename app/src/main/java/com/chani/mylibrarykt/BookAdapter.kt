package com.chani.mylibrarykt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.BookStoreApi.Book
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_book.*

class BookAdapter(val item: PageItem) : RecyclerView.Adapter<BookAdapter.BookHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder =
        BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false))

    override fun onBindViewHolder(holder: BookHolder, position: Int) =
        holder.bind(item.bookModel.books.value!![position])

    override fun getItemCount() = item.bookModel.books.value!!.size

    class BookHolder(itemView: View, override val containerView: View? = itemView) :
        RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        fun bind(book: Book) {
            titleTxt.text = book.title
            subtitleTxt.text = book.subtitle
            priceTxt.text = book.price
        }
    }
}