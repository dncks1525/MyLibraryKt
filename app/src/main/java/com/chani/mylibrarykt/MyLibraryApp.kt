package com.chani.mylibrarykt

import android.app.Application
import com.chani.mylibrarykt.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyLibraryApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        DaggerAppComponent.create().inject(this)
        super.onCreate()
    }

    override fun androidInjector() = dispatchingAndroidInjector
}