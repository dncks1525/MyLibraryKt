package com.chani.mylibrarykt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_page.*

class PageAdapter(private val items: List<PageItem>) : RecyclerView.Adapter<PageAdapter.PageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    override fun onBindViewHolder(holder: PageHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class PageHolder(itemView: View, override val containerView: View? = itemView) :
        RecyclerView.ViewHolder(itemView),
        LayoutContainer {
        fun bind(pageItem: PageItem) {
            recycler.layoutManager = GridLayoutManager(itemView.context, 2)
            recycler.setHasFixedSize(false)
            recycler.itemAnimator = null
            recycler.adapter = BookAdapter(pageItem)
        }
    }
}