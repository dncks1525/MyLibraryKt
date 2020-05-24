package com.chani.mylibrarykt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.chani.mylibrarykt.data.BookStoreApi
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {
    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isBookmarked = intent.getBooleanExtra(MyLibraryConst.KEY_BOOKMARK, false)
        val detail = intent.getParcelableExtra(MyLibraryConst.KEY_BOOK_DETAIL) as BookStoreApi.BookDetail
        titleTxt.text = detail.title
        subtitleTxt.text = detail.subtitle
        authorsTxt.text = detail.authors
        publisherTxt.text = detail.publisher
        langTxt.text = detail.language
        yearTxt.text = detail.year.toString()
        ratingTxt.text = detail.rating.toString()
        pageTxt.text = detail.pages.toString()
        isbn10Txt.text = detail.isbn10
        isbn13Txt.text = detail.isbn13
        urlTxt.text = detail.url
        descTxt.text = detail.desc

        if (isBookmarked) bookmarkImg.setBackgroundResource(R.drawable.ic_bookmark_black_24dp)

        bookmarkImg.setOnClickListener {
            isBookmarked = !isBookmarked
            bookmarkImg.setBackgroundResource(if (isBookmarked) {
                R.drawable.ic_bookmark_black_24dp
            } else {
                R.drawable.ic_bookmark_border_black_24dp
            })
        }

        Glide.with(this)
            .load(detail.image)
            .into(thumbnailImg)
    }

    override fun onSupportNavigateUp(): Boolean {
        val i = Intent()
        i.putExtra(MyLibraryConst.KEY_BOOKMARK, isBookmarked)
        i.putExtra(MyLibraryConst.KEY_ISBN13, isbn13Txt.text)
        setResult(1, i)
        supportFinishAfterTransition()
        return super.onSupportNavigateUp()
    }
}