package com.chani.mylibrarykt.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.databinding.ItemLibraryBinding
import com.chani.mylibrarykt.ui.BookCollectionActivity
import com.chani.mylibrarykt.viewmodel.LibraryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LibraryAdapter(
    private val subjectCategories: List<String>,
    private val libraryViewModel: LibraryViewModel,
) : RecyclerView.Adapter<LibraryAdapter.LibraryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryHolder {
        return LibraryHolder(
            ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LibraryHolder, position: Int) {
        holder.bind(subjectCategories[position])
    }

    override fun getItemCount(): Int {
        return subjectCategories.size
    }

    inner class LibraryHolder(
        private val binding: ItemLibraryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) = with(binding) {
            titleTxt.text = title

            val bookAdapter = BookAdapter().apply {
                CoroutineScope(Dispatchers.Main).launch {
                    loadStateFlow.collectLatest { state ->
                        if (state.refresh == LoadState.Loading) {
                            loadingProgress.visibility = View.VISIBLE
                        } else {
                            if (loadingProgress.isVisible) {
                                loadingProgress.visibility = View.GONE
                            }
                        }
                    }
                }
            }

            bookRecycler.adapter = bookAdapter.withLoadStateFooter(BookFooterAdapter(bookAdapter::retry))
            bookRecycler.setHasFixedSize(true)

            CoroutineScope(Dispatchers.IO).launch {
                if (title == "New Releases") {
                    libraryViewModel.fetchNewBooks().collectLatest {
                        bookAdapter.submitData(it)
                    }
                } else {
                    libraryViewModel.fetchSearchResult(title).collectLatest {
                        bookAdapter.submitData(it)
                    }
                }
            }

            titleAreaCst.setOnClickListener {
                val ctx = root.context
                Intent(ctx, BookCollectionActivity::class.java).apply {
                    putExtra(AppConst.EXTRA_TITLE, title)
                    ctx.startActivity(this)
                }
            }
        }
    }
}