package com.chani.mylibrarykt.di.module

import com.chani.mylibrarykt.MyLibraryConst
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import com.chani.mylibrarykt.network.ItBookstoreApi
import com.chani.mylibrarykt.ui.MainActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class MainActivityModule {
    @Provides
    fun provideActivityBinding(mainActivity: MainActivity) =
        ActivityMainBinding.inflate(mainActivity.layoutInflater)

    @Provides
    fun provideItBookstoreApi() = Retrofit.Builder()
        .baseUrl(MyLibraryConst.IT_BOOK_STORE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ItBookstoreApi::class.java)
}