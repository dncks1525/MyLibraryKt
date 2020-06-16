package com.chani.mylibrarykt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.PageItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_page.*

class PageAdapter(private val items: List<PageItem>) : RecyclerView.Adapter<PageAdapter.PageHolder>() {
    companion object {
        const val REQUEST_LOAD_MORE = 1
        const val REQUEST_SORT_BY_TITLE_ASC = 2
        const val REQUEST_SORT_BY_TITLE_DSC = 4
        const val REQUEST_SORT_BY_PRICE_ASC = 8
        const val REQUEST_SORT_BY_PRICE_DSC = 16
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_page, parent, false)
        )

    override fun onBindViewHolder(holder: PageHolder, position: Int) = holder.bind(items[position])

    override fun onBindViewHolder(holder: PageHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val what = payloads[0] as Int
            holder.request(what)
        }
    }

    override fun getItemCount(): Int = items.size

    fun request(position: Int, what: Int) {
        notifyItemChanged(position, what)
    }

    class PageHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View? = itemView

        fun bind(item: PageItem) {
            recycler.setHasFixedSize(false)
            recycler.layoutManager = GridLayoutManager(itemView.context, 2)
            recycler.itemAnimator = null
            recycler.adapter = BookAdapter(
                item.bookModel.data.value,
                item.onItemClick
            )

            item.onScroll?.let {
                recycler.clearOnScrollListeners()
                recycler.addOnScrollListener(
                    ScrollListener(
                        it
                    )
                )
            }
        }

        fun request(what: Int) {
            recycler.adapter?.let {
                val adapter = it as BookAdapter
                adapter.request(what)
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