package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.adapter.BookAdapter
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
            backImgBtn.setOnClickListener { finish() }

            intent.getStringExtra(AppConst.EXTRA_TITLE)?.let { title ->
                titleTxt.text = title

                val adapter = BookAdapter()
                booksRecycler.adapter = adapter
                lifecycleScope.launch {
                    bookstoreViewModel.search(title).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }
    }
}