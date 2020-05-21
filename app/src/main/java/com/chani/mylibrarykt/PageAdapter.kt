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

    class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View? = itemView

        fun bind(item: PageItem) {
            recycler.setHasFixedSize(false)
            recycler.layoutManager = GridLayoutManager(itemView.context, 2)
            recycler.itemAnimator = null
            recycler.adapter = BookAdapter(item.bookModel.books.value, item.onItemClick)

            item.onScroll?.let {
                recycler.clearOnScrollListeners()
                recycler.addOnScrollListener(ScrollListener(it))
            }
        }

        class ScrollListener(private val callback: () -> Unit) : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    callback()
                }
            }
        }
    }
}