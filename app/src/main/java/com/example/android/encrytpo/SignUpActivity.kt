package com.example.android.encrytpo

import android.content.Context
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

        val users_name : EditText = findViewById(R.id.nameET)
        val switch_to_SignIn : TextView = findViewById(R.id.already_have_Account)
        val email : EditText = findViewById(R.id.mailET)
        val password: EditText = findViewById(R.id.passwordET)
        val confirmPassword: EditText = findViewById(R.id.confirmpasswordET)
        val signUpButton : Button = findViewById(R.id.signup_btn)

        val sharedPref = getSharedPreferences("encrypto" , Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val auth = FirebaseAuth.getInstance()

        switch_to_SignIn.setOnClickListener {
            val intent = Intent(this , SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        signUpButton.setOnClickListener {
            val name = users_name.text.toString()
           val emailText = email.text.toString()
           val passwordText = password.text.toString()
           val confirmPasswordText = confirmPassword.text.toString()


            // validation
            if( TextUtils.isEmpty(name)   || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(confirmPasswordText)){
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
                        editor.apply {
                            putString("name" , name)
                            apply()
                        }

                        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this , "Verification Link is Sent to Email", Toast.LENGTH_LONG).show()
                                val intent = Intent(this , SignInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{

                                Toast.makeText(this , "Not found any Email Address" + task.exception?.message, Toast.LENGTH_LONG).show()
                            }

                        }

                    }else{
                        Toast.makeText(this , "Something went wrong!" + task.exception?.message, Toast.LENGTH_LONG).show()
                    }

            }


        }


    }


}