package com.example.android.encrytpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val switch_to_signUp : TextView = findViewById(R.id.dont_have_Account)

        val email : EditText = findViewById(R.id.signinmailET)
        val password: EditText = findViewById(R.id.signinpasswordET)
        val signIn_btn : Button = findViewById(R.id.signin_btn)

        val auth = FirebaseAuth.getInstance()

        signIn_btn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) ){
                Toast.makeText(this , "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            auth.signInWithEmailAndPassword(emailText , passwordText)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        if(auth.currentUser?.isEmailVerified == true){
                            val intent = Intent(this , WelcomeSplash::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this ,"Email not verified yet. Check your mail", Toast.LENGTH_LONG).show()
                        }


                    }else{
                        Toast.makeText(this ,task.exception?.message, Toast.LENGTH_LONG).show()
                    }

                }


        }
        switch_to_signUp.setOnClickListener {
            val intent = Intent(this , SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}