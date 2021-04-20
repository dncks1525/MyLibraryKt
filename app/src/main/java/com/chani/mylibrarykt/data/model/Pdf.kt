package com.chani.mylibrarykt.data.model

import com.google.gson.annotations.SerializedName

data class Pdf(
    @SerializedName("Chapter 1") val chapter1: String?,
    @SerializedName("Chapter 2") val chapter2: String?,
    @SerializedName("Chapter 3") val chapter3: String?,
    @SerializedName("Chapter 4") val chapter4: String?,
    @SerializedName("Chapter 5") val chapter5: String?,
)