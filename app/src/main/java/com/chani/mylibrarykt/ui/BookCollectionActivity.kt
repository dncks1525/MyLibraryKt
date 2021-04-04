package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.adapter.BooksAdapter
import com.chani.mylibrarykt.databinding.ActivityBookCollectionBinding
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookCollectionActivity : AppCompatActivity() {
    private val binding: ActivityBookCollectionBinding by lazy { ActivityBookCollectionBinding.inflate(layoutInflater) }

    private val bookstoreViewModel: BookstoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            intent.getStringExtra("title")?.let { title ->
                titleTxt.text = title

                val adapter = BooksAdapter()
                booksRecycler.adapter = adapter
                lifecycleScope.launch {
                    bookstoreViewModel.search(title).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }

            backImgBtn.setOnClickListener {
                finish()
            }
        }
    }
}