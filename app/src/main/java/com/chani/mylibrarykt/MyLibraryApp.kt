package com.chani.mylibrarykt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyLibraryApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}