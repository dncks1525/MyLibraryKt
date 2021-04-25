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
import com.chani.mylibrarykt.data.LibraryRepository
import com.chani.mylibrarykt.data.model.BookDetail
import com.chani.mylibrarykt.databinding.ActivityBookDetailBinding
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

            rootCst.children.forEach { view ->
                if (view != coverImg) {
                    view.visibility = View.INVISIBLE
                }
            }

            intent.getStringExtra(AppConst.EXTRA_ISBN)?.let { isbn ->
                lifecycleScope.launch {
                    libraryRepository.fetchBookDetail(isbn).apply {
                        binding.bookDetail = this

                        if (pdf != null) {
                            val builder = SpannableStringBuilder()
                            arrayListOf(
                                pdf.chapter1,
                                pdf.chapter2,
                                pdf.chapter3,
                                pdf.chapter4,
                                pdf.chapter5
                            ).forEach { chapter ->
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

                            if (builder.isNotEmpty()) {
                                pdfTxt.movementMethod = LinkMovementMethod.getInstance()
                                pdfTxt.setText(builder, TextView.BufferType.SPANNABLE)
                                pdfTxt.visibility = View.VISIBLE
                                pdfLabelTxt.visibility = View.VISIBLE
                            }
                        }
                    }

                    rootCst.children.forEach { view ->
                        if (view.visibility == View.INVISIBLE) {
                            view.visibility = View.VISIBLE
                            view.alpha = 0f
                            view.animate()
                                .alpha(1f)
                                .setDuration(300L)
                                .start()
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