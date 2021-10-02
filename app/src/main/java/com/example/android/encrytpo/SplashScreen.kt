package com.example.android.encrytpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       val auth = FirebaseAuth.getInstance()

        handler = Handler()
        handler.postDelayed({
            // for checking account has loged in or not
            if(auth.currentUser != null){
                val intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this , SignInActivity::class.java)
                startActivity(intent)
                finish()
            }

        },1500) // 1.5 second delay
    }
}