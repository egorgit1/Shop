package com.example.deb3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.deb3.R
import com.example.deb3.databinding.UserModelBinding
import com.example.deb3.models.UserInDB

class UserAdapter(val listener: Listener) :RecyclerView.Adapter<UserAdapter.UserHolder>(){

    private val listOfUsers =ArrayList<UserInDB>()

    class UserHolder(view: View):ViewHolder(view) {
        val binding= UserModelBinding.bind(view)
        fun bind(user: UserInDB, listener: Listener)=with(binding){
            val text = "${user.surname}  ${user.name} ${user.patronymic}"
            tvInitials.text = text
            tvPassport.text = user.passportId
            itemView.setOnClickListener{
                listener.onClick(user)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_model,parent,false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(listOfUsers[position], listener)
    }

    fun clear(){
        listOfUsers.clear()
        notifyDataSetChanged()
    }

    fun addUser(user: UserInDB){
        listOfUsers.add(user)
        notifyDataSetChanged()
    }

    fun addAllUsers(list :List<UserInDB>){
        listOfUsers.clear()
        listOfUsers.addAll(list)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(user: UserInDB)
    }
}