package com.chani.mylibrarykt.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.AppConst
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
            backImgBtn.setOnClickListener { finish() }

            intent.getStringExtra(AppConst.EXTRA_ISBN)?.let { isbn ->
                lifecycleScope.launch {
                    with(api.getBookDetail(isbn)) {
                        Glide.with(root)
                            .load(image)
                            .placeholder(R.drawable.book_placeholder)
                            .centerCrop()
                            .thumbnail(0.3f)
                            .into(coverImg)

                        titleTxt.text = title
                        subtitleTxt.text = subtitle
                        authorTxt.text = authors
                        ratingTxt.text = rating
                        pagesTxt.text = pages
                        buyBtn.text = resources.getString(R.string.book_buy).format(price)
                        describeTxt.text = desc
                        yearTxt.text = year
                        publisherTxt.text = publisher
                        langTxt.text = language
                        isbn10Txt.text = isbn10
                        isbn13Txt.text = isbn13
                    }
                }
            }
        }
    }
}