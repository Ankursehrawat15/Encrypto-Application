package com.example.android.encrytpo


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddPassword : AppCompatActivity() {

    private lateinit var strengthLevel_txt : TextView
    private lateinit var  Strengthindicator : View
    private lateinit var lowerCase : TextView
    private  lateinit var lowerImg : ImageView
    private  lateinit var upperCase : TextView
    private lateinit var upperImg : ImageView
    private lateinit var digitImg : ImageView
    private  lateinit var specialImg : ImageView
    private lateinit var digit : TextView
    private  lateinit var specialChar : TextView
    private  lateinit var name : EditText
    private lateinit var set_pass : EditText
    private  lateinit var add : Button
    private  lateinit var got_to_random : TextView

    private var color: Int = R.color.weak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)
        supportActionBar?.title = "Wallet"

        strengthLevel_txt = findViewById(R.id.strengthLevelTV)
        Strengthindicator  = findViewById(R.id.strengthLevel_indicator)
        lowerCase = findViewById(R.id.lowerCaseTV)
         lowerImg  = findViewById(R.id.check1)
         upperCase  = findViewById(R.id.UpperCaseTV)
         upperImg = findViewById(R.id.check2)
          digitImg = findViewById(R.id.check3)
         specialImg = findViewById(R.id.check4)
        digit  = findViewById(R.id.digitsV)
         specialChar  = findViewById(R.id.specialCharsTV)
        got_to_random = findViewById(R.id.add_to_generatePassTV)

        name = findViewById(R.id.app_website_NameET)
        set_pass  = findViewById(R.id.SetPasswordET)
        add  = findViewById(R.id.add_to_Database)


        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()


        got_to_random.setOnClickListener {
            val intent = Intent(this , GeneratePasswordActivity::class.java)
            startActivity(intent)
        }
        

        val passwordStrengthCalculator = PasswordStrengthCalculator()
        set_pass.addTextChangedListener(passwordStrengthCalculator)

        passwordStrengthCalculator.strengthLevel.observe(this, Observer {strengthLevel ->
            displayStrengthLevel(strengthLevel)
        })
        passwordStrengthCalculator.strengthColor.observe(this, Observer {strengthColor ->
            color = strengthColor
        })

        passwordStrengthCalculator.lowerCase.observe(this, Observer {value ->
            displayPasswordSuggestions(value, lowerImg, lowerCase)
        })
        passwordStrengthCalculator.upperCase.observe(this, Observer {value ->
            displayPasswordSuggestions(value, upperImg, upperCase)
        })
        passwordStrengthCalculator.digit.observe(this, Observer {value ->
            displayPasswordSuggestions(value, digitImg, digit)
        })
        passwordStrengthCalculator.specialChar.observe(this, Observer {value ->
            displayPasswordSuggestions(value, specialImg, specialChar)
        })


        add.setOnClickListener {
               val app_name = name.text.toString()
               var pass = set_pass.text.toString()

            if(TextUtils.isEmpty(app_name) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(app_name)){
                Toast.makeText(this, "Fields Cannot be Empty",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pass.length < 8 ){
                Toast.makeText(this, "Password should consist of more than 8 characters",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val obj : Aes = Aes()
             pass =  obj.encrypt(pass , app_name+auth.currentUser?.uid.toString())
            if(! TextUtils.isEmpty(pass)) {
                database.reference.child(auth.currentUser?.uid!!).child(app_name).setValue(pass.subSequence(0,pass.length-1))
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG).show()
                                name.text.clear()
                                set_pass.text.clear()
                            } else {
                                Toast.makeText(this, "" + it.exception?.message, Toast.LENGTH_LONG).show()
                            }
                        }


            }else{
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if(value == 1){
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.bulletproof))
            textView.setTextColor(ContextCompat.getColor(this, R.color.bulletproof))
        }else{
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        if(strengthLevel == StrengthLevel.STRONG || strengthLevel == StrengthLevel.BULLETPROOF) {
            add.isEnabled
        }
        strengthLevel_txt.text = strengthLevel.name
        strengthLevel_txt.setTextColor(ContextCompat.getColor(this, color))
        Strengthindicator.setBackgroundColor(ContextCompat.getColor(this, color))
    }


}