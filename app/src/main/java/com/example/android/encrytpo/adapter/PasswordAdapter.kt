package com.example.android.encrytpo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.encrytpo.ListPasswords
import com.example.android.encrytpo.R

class PasswordAdapter(val list: List<ListPasswords>) : RecyclerView.Adapter<PasswordAdapter.ListViewHolder>() {

    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val app_web_name : TextView = itemView.findViewById(R.id.app_web_name)
        val pass : TextView = itemView.findViewById(R.id.password)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
          val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_password , parent , false)
          return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
           holder.app_web_name.text = list[position].appName
           holder.pass.text = "Password: "+list[position].password
    }

    override fun getItemCount(): Int {
      return list.size
    }
}