package com.chani.mylibrarykt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.databinding.ItemBookstoreBinding
import com.chani.mylibrarykt.ui.BookCollectionActivity
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookstoreAdapter(
    private val dataList: List<String>,
    private val bookstoreViewModel: BookstoreViewModel,
) : RecyclerView.Adapter<BookstoreAdapter.BookstoreHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookstoreHolder {
        return BookstoreHolder(
            ItemBookstoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookstoreHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class BookstoreHolder(
        private val binding: ItemBookstoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) = with(binding) {
            titleTxt.text = title

            val adapter = BookAdapter()
            bookRecycler.setHasFixedSize(true)
            bookRecycler.adapter = adapter
            CoroutineScope(Dispatchers.IO).launch {
                if (title == "New Releases") {
                    bookstoreViewModel.getNewBooks().collectLatest {
                        adapter.submitData(it)
                    }
                } else {
                    bookstoreViewModel.search(title).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }

            titleAreaCst.setOnClickListener {
                val ctx = root.context
                with(Intent(ctx, BookCollectionActivity::class.java)) {
                    putExtra(AppConst.EXTRA_TITLE, title)
                    ctx.startActivity(this)
                }
            }
        }
    }
}