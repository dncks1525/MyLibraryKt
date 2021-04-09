package com.chani.mylibrarykt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.databinding.ItemBookstoreBinding
import com.chani.mylibrarykt.ui.BookCollectionActivity
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookstoreAdapter(
    private val subjectCategories: List<String>,
    private val bookstoreViewModel: BookstoreViewModel,
) : RecyclerView.Adapter<BookstoreAdapter.BookstoreHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookstoreHolder {
        return BookstoreHolder(
            ItemBookstoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookstoreHolder, position: Int) {
        holder.bind(subjectCategories[position])
    }

    override fun getItemCount(): Int {
        return subjectCategories.size
    }

    inner class BookstoreHolder(
        private val binding: ItemBookstoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) = with(binding) {
            titleTxt.text = title

            val bookAdapter = BookAdapter()
            bookRecycler.adapter = bookAdapter
            bookRecycler.setHasFixedSize(true)

            CoroutineScope(Dispatchers.IO).launch {
                if (title == "New Releases") {
                    bookAdapter.loadStateFlow.collectLatest {
                        AppLog.d("bookAdapter new ${bookAdapter.itemCount}")
                    }

                    bookstoreViewModel.getNewBooks().collectLatest {
                        bookAdapter.submitData(it)
                    }
                } else {
                    bookAdapter.loadStateFlow.collectLatest {
                        AppLog.d("bookAdapter search ${bookAdapter.itemCount}")
                    }

                    bookstoreViewModel.search(title).collectLatest {
                        bookAdapter.submitData(it)
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