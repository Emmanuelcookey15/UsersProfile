package com.LineCard.userprofileprojectquestionone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.LineCard.userprofileprojectquestionone.R
import com.LineCard.userprofileprojectquestionone.model.Users

class UsersAdapter(): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var users: List<Users> = ArrayList()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_item,parent, false))
    }

    fun setUsers(users: List<Users>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return users.size
    }


    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        val currentUser: Users = users[position]
        holder.textViewName!!.text = currentUser.name
        holder.textViewEmail!!.text = currentUser.email
        holder.textViewUsernme!!.text = currentUser.username
        holder.itemView.setOnClickListener{
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener!!.onItemClick(users[position])
            }
        }

    }



    class UsersViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){

        val textViewName = itemView.findViewById<TextView?>(R.id.text_view_name)
        val textViewEmail = itemView.findViewById<TextView?>(R.id.text_view_email)
        val textViewUsernme = itemView.findViewById<TextView?>(R.id.text_view_username)
        val fullCardView = itemView.findViewById<CardView>(R.id.full_card)


    }


    interface OnItemClickListener {
        fun onItemClick(user: Users?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }


}


