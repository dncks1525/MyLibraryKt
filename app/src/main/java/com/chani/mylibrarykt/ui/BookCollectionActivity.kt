package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.adapter.BookAdapter
import com.chani.mylibrarykt.databinding.ActivityBookCollectionBinding
import com.chani.mylibrarykt.databinding.ContentSubjectBinding
import com.chani.mylibrarykt.viewmodel.BookstoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookCollectionActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityBookCollectionBinding
    private val bookstoreViewModel: BookstoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val subjectBinding = ContentSubjectBinding.bind(binding.root)

        with(binding) {
            subjectBinding.backImgbtn.setOnClickListener { finish() }

            intent.getStringExtra(AppConst.EXTRA_TITLE)?.let { title ->
                subjectBinding.titleTxt.text = title

                val adapter = BookAdapter()
                bookRecycler.adapter = adapter
                lifecycleScope.launch {
                    bookstoreViewModel.search(title).collectLatest {
                        adapter.submitData(it)
                    }
                }
            }
        }
    }
}