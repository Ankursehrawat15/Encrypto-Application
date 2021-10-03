package com.example.android.encrytpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var hash : Button
    private lateinit var generatePassword : Button
    private lateinit var vault : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       generatePassword = findViewById(R.id.button2)
        vault = findViewById(R.id.button3)
        hash = findViewById(R.id.button)
        hash.setOnClickListener {
            val intent : Intent = Intent(this , DashBoardActivity::class.java)
            startActivity(intent)

        }

        generatePassword.setOnClickListener {
            val intent : Intent = Intent(this , GeneratePasswordActivity::class.java)
            startActivity(intent)
        }

        vault.setOnClickListener {
            val intent : Intent = Intent(this , FingerPrintAuthentication::class.java)
            startActivity(intent)
        }




    }
}