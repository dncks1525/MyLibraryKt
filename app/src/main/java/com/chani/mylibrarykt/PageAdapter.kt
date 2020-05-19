package com.chani.mylibrarykt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.BookStoreApi.Book
import com.chani.mylibrarykt.pages.PageInfo

class PageAdapter(private val items: MutableList<PageInfo>) : RecyclerView.Adapter<PageAdapter.PageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    override fun onBindViewHolder(holder: PageHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pageInfo: PageInfo) {

        }
    }
}