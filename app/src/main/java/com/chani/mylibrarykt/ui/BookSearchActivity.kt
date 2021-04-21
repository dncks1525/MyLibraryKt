package com.chani.mylibrarykt.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.chani.mylibrarykt.adapter.BookAdapter
import com.chani.mylibrarykt.adapter.BookFooterAdapter
import com.chani.mylibrarykt.data.BookListType
import com.chani.mylibrarykt.databinding.ActivityBookSearchBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookSearchActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityBookSearchBinding
    @Inject lateinit var searchRecentSuggestions: SearchRecentSuggestions
    private val libraryViewModel: LibraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            val bookAdapter = BookAdapter(BookListType.LIST).apply {
                lifecycleScope.launch {
                    loadStateFlow.collectLatest { state ->
                        if (state.refresh == LoadState.Loading) {
                            loadingProgress.visibility = View.VISIBLE
                        } else {
                            if (loadingProgress.isVisible) {
                                if (itemCount == 0) {
                                    noContentsTxt.visibility = View.VISIBLE
                                } else {
                                    searchRecentSuggestions.saveRecentQuery(quickSearch.query.toString(), null)
                                }

                                loadingProgress.visibility = View.GONE
                            }
                        }
                    }
                }
            }
            bookRecycler.adapter = bookAdapter.withLoadStateFooter(BookFooterAdapter(bookAdapter::retry))
            bookRecycler.setHasFixedSize(true)

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            quickSearch.apply {
                onActionViewExpanded()
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            clearFocus()
                            lifecycleScope.launch {
                                libraryViewModel.fetchSearchResult(query).collectLatest {
                                    bookAdapter.submitData(it)
                                }
                            }
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })

                findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon).setOnClickListener {
                    clearFocus()
                    supportFinishAfterTransition()
                }
            }

            var isKeyboardShowing = false
            root.viewTreeObserver.addOnGlobalLayoutListener {
                Rect().apply {
                    root.getWindowVisibleDisplayFrame(this)
                    val diff = root.rootView.height - bottom
                    if (diff > (root.rootView.height / 4)) {
                        isKeyboardShowing = true
                    } else {
                        if (isKeyboardShowing && quickSearch.query.isEmpty()) {
                            supportFinishAfterTransition()
                        }
                        isKeyboardShowing = false
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.let { query ->
                binding.quickSearch.clearFocus()
                binding.quickSearch.setQuery(query, true)
            }
        }
    }
}