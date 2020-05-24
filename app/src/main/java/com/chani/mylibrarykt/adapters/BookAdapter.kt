package com.chani.mylibrarykt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.BookStoreApi.Book
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_book.*

class BookAdapter(
    private var items: MutableList<Book>?,
    private val onItemClick: ((view: View, book: Book) -> Unit)?
) : RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder =
        BookHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)
        )

    override fun onBindViewHolder(holder: BookHolder, position: Int) = holder.bind(items?.get(position), onItemClick)

    override fun getItemCount() = items?.size ?: 0

    fun request(what: Int) {
        when (what) {
            PageAdapter.REQUEST_LOAD_MORE -> {
                println("BookAdapter request size = $itemCount")
                notifyItemInserted(itemCount)
            }
            PageAdapter.REQUEST_SORT_BY_TITLE_ASC -> {
                println("BookAdapter REQUEST_SORT_BY_TITLE_ASC")
                items?.sortedWith(compareBy { it.title })
                    ?.also { items = it.toMutableList() }
                notifyDataSetChanged()
            }
            PageAdapter.REQUEST_SORT_BY_TITLE_DSC -> {
                println("BookAdapter REQUEST_SORT_BY_TITLE_DSC")
                items?.sortedWith(compareByDescending { it.title })
                    ?.also { items = it.toMutableList() }
                notifyDataSetChanged()
            }
            PageAdapter.REQUEST_SORT_BY_PRICE_ASC -> {
                println("BookAdapter REQUEST_SORT_BY_PRICE_ASC")
                items?.sortedWith(compareBy { it.price.substring(1).toFloat() })
                    ?.also { items = it.toMutableList() }
                notifyDataSetChanged()
            }
            PageAdapter.REQUEST_SORT_BY_PRICE_DSC -> {
                println("BookAdapter REQUEST_SORT_BY_PRICE_DSC")
                items?.sortedWith(compareByDescending { it.price.substring(1).toFloat() })
                    ?.also { items = it.toMutableList() }
                notifyDataSetChanged()
            }
        }
    }

    class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View? = itemView

        fun bind(book: Book?, onItemClick: ((view: View, book: Book) -> Unit)?) {
            book?.let { b ->
                titleTxt.text = b.title
                subtitleTxt.text = b.subtitle
                priceTxt.text = b.price
                Glide.with(itemView)
                    .load(b.image)
                    .thumbnail(0.1f)
                    .into(thumbnailImg)

                if (onItemClick != null) {
                    itemView.setOnClickListener {
                        onItemClick.invoke(thumbnailImg, b)
                    }
                }
            }
        }
    }

}