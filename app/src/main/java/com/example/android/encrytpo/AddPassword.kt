package com.example.android.encrytpo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.biometric.FingerprintDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)

        val name : EditText = findViewById(R.id.app_website_NameET)
        val set_pass : EditText = findViewById(R.id.SetPasswordET)
        val add : Button = findViewById(R.id.add_to_Database)
        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        add.setOnClickListener {

            if(TextUtils.isEmpty(name.toString()) || TextUtils.isEmpty(set_pass.toString())){
                Toast.makeText(this, "Fields Cannot be Empty",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            database.reference.child(auth.currentUser?.uid!!).child(name.text.toString()).setValue(set_pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                      Toast.makeText(this, "Data saved successfully",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, ""+it.exception?.message,Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}