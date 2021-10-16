package com.example.android.encrytpo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var hash : CardView
    private lateinit var generatePassword : CardView
    private lateinit var vault : CardView
    private lateinit var aboutUs : CardView
    private lateinit var signOut : ImageView
    private lateinit var passCount : TextView
    private lateinit var generatedpass_count : TextView

    val auth = FirebaseAuth.getInstance()
    val data = FirebaseDatabase.getInstance()
    val ref = data.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val logoutDialog = AlertDialog.Builder(this).setTitle("Logout").setMessage("Do you want to logout ?")
            .setIcon(R.drawable.logouticon)
            .setPositiveButton("Yes") { _, _ ->
                auth.signOut()
                val intent : Intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()

            }.setNegativeButton("No") { dialogInterface , i ->

                dialogInterface.cancel()
            }

         generatedpass_count = findViewById(R.id.genrated_passCount)
         val name : TextView = findViewById(R.id.dashBoardHome_Tv)
         generatePassword = findViewById(R.id.pass_generator_Cv)
         vault = findViewById(R.id.secure_wallet_Cv)
         hash = findViewById(R.id.hash_generator_Cv)
         signOut = findViewById(R.id.signOut)
         passCount = findViewById(R.id.password_count)
         aboutUs = findViewById(R.id.aboutUSCV)

         val sharedpref = getSharedPreferences("encrypto", Context.MODE_PRIVATE)
         val string = sharedpref.getString("name" , "there")
         name.text = "Hello, "+string+"👋"





        signOut.setOnClickListener {
           logoutDialog.show()
        }


        hash.setOnClickListener {
            val intent : Intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)

        }

        generatePassword.setOnClickListener {
            val intent : Intent = Intent(this, GeneratePasswordActivity::class.java)
            startActivity(intent)
        }

        vault.setOnClickListener {
            val intent : Intent = Intent(this, FingerPrintAuthentication::class.java)
            startActivity(intent)
        }


        aboutUs.setOnClickListener {
            val intent : Intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }



    }


    override fun onResume() {
        super.onResume()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null ) {
                    var count : Long = 0
                    generatedpass_count.text = snapshot.childrenCount.toString()
                    for (snap in snapshot.children) {

                        if(TextUtils.equals(snap.key.toString() , auth.currentUser?.uid.toString())){
                            count += snap.childrenCount
                        }
                    }
                    passCount.text = count.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}