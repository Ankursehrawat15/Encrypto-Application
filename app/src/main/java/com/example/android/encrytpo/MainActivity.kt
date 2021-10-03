package com.example.android.encrytpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var button : Button
    private lateinit var button2 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button2 = findViewById(R.id.button2)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            val intent : Intent = Intent(this , DashBoardActivity::class.java)
            startActivity(intent)

        }

        button2.setOnClickListener {
            val intent : Intent = Intent(this , GeneratePasswordActivity::class.java)
            startActivity(intent)
        }




    }
}