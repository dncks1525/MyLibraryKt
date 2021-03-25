package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideActivityBinding(mainActivity: MainActivity) = ActivityMainBinding.inflate(mainActivity.layoutInflater)
}