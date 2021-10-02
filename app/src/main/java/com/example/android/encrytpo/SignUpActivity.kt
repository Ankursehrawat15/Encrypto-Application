package com.example.android.encrytpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val switch_to_SignIn : TextView = findViewById(R.id.already_have_Account)
        val email : EditText = findViewById(R.id.mailET)
        val password: EditText = findViewById(R.id.passwordET)
        val confirmPassword: EditText = findViewById(R.id.confirmpasswordET)
        val signUpButton : Button = findViewById(R.id.signup_btn)


        val auth = FirebaseAuth.getInstance()

        switch_to_SignIn.setOnClickListener {
            val intent = Intent(this , SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        signUpButton.setOnClickListener {
           val emailText = email.text.toString()
           val passwordText = password.text.toString()
           val confirmPasswordText = confirmPassword.text.toString()


            // validation
            if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(confirmPasswordText)){
                 Toast.makeText(this , "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(passwordText.length < 6){
                Toast.makeText(this , "Password should have atLeast 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordText != confirmPasswordText){
                Toast.makeText(this , "oops password did not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
                // creating account
            auth.createUserWithEmailAndPassword(emailText,passwordText)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val intent = Intent(this , WelcomeSplash::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this , "Something went wrong!" + task.exception?.message, Toast.LENGTH_LONG).show()
                    }

            }


        }


    }


}