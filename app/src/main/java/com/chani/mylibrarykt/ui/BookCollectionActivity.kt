package com.chani.mylibrarykt.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.adapter.BookAdapter
import com.chani.mylibrarykt.adapter.BookFooterAdapter
import com.chani.mylibrarykt.databinding.ActivityBookCollectionBinding
import com.chani.mylibrarykt.databinding.ContentSubjectBinding
import com.chani.mylibrarykt.viewmodel.LibraryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookCollectionActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityBookCollectionBinding
    private val libraryViewModel: LibraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            val subjectBinding = ContentSubjectBinding.bind(root)
            subjectBinding.backImgbtn.setOnClickListener { finish() }

            intent.getStringExtra(AppConst.EXTRA_TITLE)?.let { title ->
                subjectBinding.titleTxt.text = title

                val adapter = BookAdapter().apply {
                    lifecycleScope.launch {
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
                bookRecycler.adapter = adapter.withLoadStateFooter(BookFooterAdapter(adapter::retry))
                bookRecycler.setHasFixedSize(true)

                lifecycleScope.launch {
                    if (title == "New Releases") {
                        libraryViewModel.fetchNewBooks().collectLatest {
                            adapter.submitData(it)
                        }
                    } else {
                        libraryViewModel.fetchSearchResult(title).collectLatest {
                            adapter.submitData(it)
                        }
                    }
                }
            }
        }
    }
}