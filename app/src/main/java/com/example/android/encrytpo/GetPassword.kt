package com.example.android.encrytpo

import android.app.LauncherActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.encrytpo.adapter.PasswordAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GetPassword : AppCompatActivity() {

    private lateinit var  list : MutableList<ListPasswords>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_password)

        list = mutableListOf()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = PasswordAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseDatabase.reference.child(firebaseAuth.currentUser?.uid.toString()).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               list.clear()
                for(child in snapshot.children){
                       list.add(ListPasswords(child.key.toString() , child.value.toString()))
                }

                (recyclerView.adapter as  PasswordAdapter ).notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.toString())
            }

        })

    }
}