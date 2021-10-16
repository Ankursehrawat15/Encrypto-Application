package com.example.android.encrytpo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        supportActionBar?.hide()
    }
}