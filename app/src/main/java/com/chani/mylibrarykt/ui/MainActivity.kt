package com.chani.mylibrarykt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chani.mylibrarykt.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}