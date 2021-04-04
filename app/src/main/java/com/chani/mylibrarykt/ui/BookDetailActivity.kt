package com.chani.mylibrarykt.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.remote.BookstoreApi
import com.chani.mylibrarykt.databinding.ActivityBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {
    private val binding: ActivityBookDetailBinding by lazy { ActivityBookDetailBinding.inflate(layoutInflater) }

    @Inject lateinit var api: BookstoreApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            backImgBtn.setOnClickListener {
                finish()
            }

            intent.getStringExtra("isbn")?.let { isbn ->
                lifecycleScope.launch {
                    api.getBookDetail(isbn).also { bookDetail ->
                        with(bookDetail) {
                            titleTxt.text = title
                            subtitleTxt.text = subtitle
                            authorTxt.text = authors
                            yearTxt.text = year
                            publisherTxt.text = publisher
                            describeTxt.text = desc
                            pagesTxt.text = pages
                            ratingTxt.text = rating
                            buyBtn.text = "$price BUY"
                            langTxt.text = language
                            isbn10Txt.text = isbn10
                            isbn13Txt.text = isbn13

                            Glide.with(root)
                                .load(image)
                                .placeholder(R.drawable.book_placeholder)
                                .centerCrop()
                                .thumbnail(0.1f)
                                .into(coverImg)
                        }
                    }
                }
            }
        }
    }
}