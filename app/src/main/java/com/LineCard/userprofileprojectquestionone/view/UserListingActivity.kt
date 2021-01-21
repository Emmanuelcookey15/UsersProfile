package com.LineCard.userprofileprojectquestionone.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.LineCard.userprofileprojectquestionone.R
import com.LineCard.userprofileprojectquestionone.adapter.UsersAdapter
import com.LineCard.userprofileprojectquestionone.model.Users
import com.LineCard.userprofileprojectquestionone.utils.isConnectedToTheInternet
import com.LineCard.userprofileprojectquestionone.viewmodel.UsersViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListingActivity : AppCompatActivity() {

    lateinit var viewmodel: UsersViewModel

    val ADD_USER_REQUEST = 1
    val DETAIL_USERS_REQUEST = 2

    lateinit var buttonAddNote: FloatingActionButton

    var Empty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewmodel = ViewModelProvider(this).get(UsersViewModel::class.java)

        supportActionBar!!.title = "Users Listing"

        Empty = checkIfDataBaseEmpty()

        getUsersRemotely()

        addNewUser()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_note)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val adapterUser = UsersAdapter()
        recyclerView.adapter = adapterUser

        adapterUser.setOnItemClickListener(object : UsersAdapter.OnItemClickListener{
            override fun onItemClick(user: Users?) {
                val intent = Intent(this@UserListingActivity, UserDetailActivity::class.java)
                intent.putExtra("user_id", user!!.id)
                Log.d("THE_USER_ID", "${user.id}")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        })

        viewmodel.getAllUsers().observe(this, Observer {
            adapterUser.setUsers(it)
        })



    }



    fun addNewUser(){
        if (!Empty) {
            buttonAddNote = findViewById(R.id.button_add_note)
            buttonAddNote.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, AddNewUserActivity::class.java)
                startActivityForResult(
                    intent, ADD_USER_REQUEST
                )
                overridePendingTransition(R.anim.slide_up, R.anim.slide_up)
            })
        }else {
            Toast.makeText(this, "Load remote users before adding users locally. Check Internet connection.", Toast.LENGTH_LONG).show()
        }
    }



    fun getUsersRemotely(){
        if (isConnectedToTheInternet()) {
            if (Empty) {
                viewmodel.getUsers()?.observe(this, Observer { list ->
                    list?.forEach {
                        viewmodel.insertResponse(it)
                        Log.d("THE_IDS", "${it.id}")
                    }
                })
            }
        } else {
            Toast.makeText(this, "Internet Connect needed to update data", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkIfDataBaseEmpty(): Boolean {
        var emptyDB = true
        viewmodel.userNumbers().observe(this, Observer {
            if (it > 0){
                emptyDB = false
            }
        })

        return emptyDB
    }


}
