package com.chani.mylibrarykt.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BookAdapter
import com.chani.mylibrarykt.data.enum.BookType
import com.chani.mylibrarykt.databinding.ActivitySearchBinding
import com.chani.mylibrarykt.util.AppLog
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    private val bookstoreViewModel: BookstoreViewModel by viewModels()
    @Inject lateinit var searchRecentSuggestions: SearchRecentSuggestions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            val adapter = BookAdapter(BookType.LIST)
            bookRecycler.setHasFixedSize(true)
            bookRecycler.adapter = adapter

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            with(quickSearch) {
                onActionViewExpanded()
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            clearFocus()
                            searchRecentSuggestions.saveRecentQuery(query, null)

                            lifecycleScope.launch {
                                bookstoreViewModel.search(query).collectLatest {
                                    adapter.submitData(it)
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
                    supportFinishAfterTransition()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                AppLog.d("query = $query")
                binding.quickSearch.setQuery(query, false)
                binding.quickSearch.clearFocus()
            }
        }
    }
}