package com.example.android.encrytpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class WelcomeSplash : AppCompatActivity() {
    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_splash)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        },2500) // 2 second delay
    }
}