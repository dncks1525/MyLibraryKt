/**
 * Copyright 2021 Lee Woochan <dncks1525@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chani.mylibrarykt

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

fun ImageView.toByteArray(): ByteArray? {
    val bitmapDrawable = drawable as? BitmapDrawable
    if (bitmapDrawable != null) {
        ByteArrayOutputStream().apply {
            bitmapDrawable.bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            return toByteArray()
        }
    }

    return null
}

@BindingAdapter("app:bindImage")
fun ImageView.bindImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.book_placeholder)
        .centerCrop()
        .thumbnail(0.3f)
        .into(this)
}