package com.chani.mylibrarykt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.databinding.ItemBookFooterBinding

class BookFooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<BookFooterAdapter.BookFooterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BookFooterHolder {
        return BookFooterHolder(
            ItemBookFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookFooterHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class BookFooterHolder(
        private val binding: ItemBookFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) = with(binding) {
            loadingProgress.visibility = View.GONE
            retryBtn.visibility = View.GONE
            if (!retryBtn.hasOnClickListeners()) {
                retryBtn.setOnClickListener { retry() }
            }

            if (loadState is LoadState.Loading) {
                loadingProgress.visibility = View.VISIBLE
            } else if (loadState is LoadState.Error) {
                retryBtn.visibility = View.VISIBLE
            }
        }
    }
}