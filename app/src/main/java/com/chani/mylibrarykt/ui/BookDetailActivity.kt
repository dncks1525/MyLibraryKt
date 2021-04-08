package com.chani.mylibrarykt.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.repository.local.dao.HistoryDao
import com.chani.mylibrarykt.data.repository.local.entity.History
import com.chani.mylibrarykt.data.repository.remote.BookstoreApi
import com.chani.mylibrarykt.data.repository.remote.model.BookDetail
import com.chani.mylibrarykt.databinding.ActivityBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {
    private val binding: ActivityBookDetailBinding by lazy { ActivityBookDetailBinding.inflate(layoutInflater) }

    @Inject lateinit var bookstoreApi: BookstoreApi
    @Inject lateinit var historyDao: HistoryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            backImgbtn.setOnClickListener { supportFinishAfterTransition() }
            buyBtn.setOnClickListener {
                Toast.makeText(this@BookDetailActivity, "Thank you!", Toast.LENGTH_SHORT).show()
            }

            val imgByteArray = intent.getByteArrayExtra(AppConst.EXTRA_COVER)
            if (imgByteArray != null) {
                coverImg.setImageBitmap(BitmapFactory.decodeByteArray(imgByteArray, 0, imgByteArray.size))
            }

            intent.getStringExtra(AppConst.EXTRA_ISBN)?.let { isbn ->
                lifecycleScope.launch {
                    root.children.forEach { v ->
                        if (v != coverImg) {
                            v.visibility = View.INVISIBLE
                        }
                    }

                    val bookDetail = bookstoreApi.getBookDetail(isbn).apply {
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

                    lifecycleScope.launch(Dispatchers.IO) {
                        if (imgByteArray != null) {
                            saveToRecentHistoryDatabase(imgByteArray, bookDetail)
                        }
                    }
                }
            }
        }
    }

    private fun saveToRecentHistoryDatabase(arr: ByteArray, bookDetail: BookDetail) {
        val imgFile = File(filesDir, "${bookDetail.isbn13}.png")
        if (!imgFile.exists()) {
            try {
                FileOutputStream(imgFile).apply {
                    write(arr)
                    flush()
                    close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val timestamp = Calendar.getInstance().timeInMillis

        historyDao.insert(History(bookDetail.toBook(), imgFile.path, timestamp))
    }
}