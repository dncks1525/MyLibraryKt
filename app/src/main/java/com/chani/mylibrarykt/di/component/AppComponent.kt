package com.chani.mylibrarykt.di.component

import com.chani.mylibrarykt.MyLibraryApp
import com.chani.mylibrarykt.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
    ]
)
interface AppComponent : AndroidInjector<MyLibraryApp>