package com.chani.mylibrarykt.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
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
            backImgBtn.setOnClickListener { supportFinishAfterTransition() }

            intent.getByteArrayExtra(AppConst.EXTRA_COVER)?.let {
                coverImg.setImageBitmap(BitmapFactory.decodeByteArray(it, 0, it.size))
            }

            intent.getStringExtra(AppConst.EXTRA_ISBN)?.let { isbn ->
                lifecycleScope.launch {
                    root.children.forEach { v ->
                        if (v != coverImg) {
                            v.visibility = View.INVISIBLE
                        }
                    }

                    with(api.getBookDetail(isbn)) {
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

                        root.children.forEach { v ->
                            if (v.visibility == View.INVISIBLE) {
                                v.visibility = View.VISIBLE
                                v.alpha = 0f
                                v.animate()
                                    .alpha(1f)
                                    .setDuration(300L)
                                    .start()
                            }
                        }
                    }
                }
            }
        }
    }
}