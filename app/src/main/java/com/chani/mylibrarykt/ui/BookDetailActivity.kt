package com.chani.mylibrarykt.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.chani.mylibrarykt.AppConst
import com.chani.mylibrarykt.R
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.BookDetail
import com.chani.mylibrarykt.databinding.ActivityBookDetailBinding
import com.chani.mylibrarykt.toBook
import com.chani.mylibrarykt.util.AppLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {
    @Inject lateinit var binding: ActivityBookDetailBinding
    @Inject lateinit var libraryRepository: LibraryRepository

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

                    val bookDetail = libraryRepository.fetchBookDetail(isbn).apply {
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

                        with(pdf) {
                            val builder = SpannableStringBuilder()
                            arrayListOf(chapter1, chapter2, chapter3, chapter4, chapter5).forEach { chapter ->
                                if (chapter != null) {
                                    builder.append(chapter)
                                    builder.setSpan(object : ClickableSpan() {
                                        override fun onClick(widget: View) {
                                            Intent(Intent.ACTION_VIEW, Uri.parse(chapter)).apply {
                                                startActivity(this)
                                            }
                                        }
                                    }, builder.length - chapter.length, builder.length, 0)
                                }
                            }

                            pdfTxt.movementMethod = LinkMovementMethod.getInstance()
                            pdfTxt.setText(builder, TextView.BufferType.SPANNABLE)
                        }

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

                    withContext(Dispatchers.IO) {
                        val isNoHistory = intent.getBooleanExtra(AppConst.EXTRA_NO_HISTORY, false)
                        if (!isNoHistory && imgByteArray != null) {
                            saveRecentBook(bookDetail, imgByteArray)
                        }
                    }
                }
            }
        }
    }

    private fun saveRecentBook(bookDetail: BookDetail, arr: ByteArray) {
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

        libraryRepository.insert(bookDetail.toBook(imgFile.path))
    }
}