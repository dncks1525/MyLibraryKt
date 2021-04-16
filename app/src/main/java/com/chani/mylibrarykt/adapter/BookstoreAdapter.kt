package com.chani.mylibrarykt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.paging.LoadState
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
import okhttp3.Dispatcher

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

            val bookAdapter = BookAdapter().apply {
                CoroutineScope(Dispatchers.Main).launch {
                    var isFirstRun = true
                    loadStateFlow.collectLatest { state ->
                        // todo: working but the state should be checked.
                        AppLog.d("bookstore state = $state")

                        if (isFirstRun) {
                            isFirstRun = false
                            return@collectLatest
                        }

                        if (state.refresh == LoadState.Loading) {
                            contentsGroup.visibility = View.GONE
                            loadingProgress.visibility = View.VISIBLE
                        } else {
                            contentsGroup.visibility = View.VISIBLE
                            loadingProgress.visibility = View.GONE
                        }
                    }
                }
            }

            bookRecycler.adapter = bookAdapter
            bookRecycler.setHasFixedSize(true)

            CoroutineScope(Dispatchers.IO).launch {
                if (title == "New Releases") {
                    bookstoreViewModel.getNewBooks().collectLatest {
                        bookAdapter.submitData(it)
                    }
                } else {
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