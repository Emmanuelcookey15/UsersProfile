package com.LineCard.userprofileprojectquestionone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.LineCard.userprofileprojectquestionone.R
import com.LineCard.userprofileprojectquestionone.model.Users
import java.lang.String

class UsersAdapter(private val ctx: Context, arrays: ArrayList<Users>): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var contentArray = arrays

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_item,parent, false))
    }

    override fun getItemCount(): Int {
        return contentArray.size
    }

    fun setCartData(dataCartPackage: ArrayList<Users>) {
        contentArray = dataCartPackage
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        val currentUser: Users = contentArray.get(position)
        holder.textViewName.text = currentUser.name
        holder.textViewEmail.text = currentUser.email
        holder.textViewUsernme.text = currentUser.username

    }



    class UsersViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){

        val textViewName = itemView.findViewById<TextView?>(R.id.text_view_name)
        val textViewEmail = itemView.findViewById<TextView?>(R.id.text_view_email)
        val textViewUsernme = itemView.findViewById<TextView?>(R.id.text_view_username)

    }
}