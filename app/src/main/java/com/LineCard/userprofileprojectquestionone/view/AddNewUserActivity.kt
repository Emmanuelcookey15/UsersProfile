package com.LineCard.userprofileprojectquestionone.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.LineCard.userprofileprojectquestionone.R
import com.LineCard.userprofileprojectquestionone.model.Users
import com.LineCard.userprofileprojectquestionone.viewmodel.UsersViewModel

class AddNewUserActivity : AppCompatActivity() {


    lateinit var userAddName: EditText
    lateinit var userAddUsername: EditText
    lateinit var userAddEmail: EditText
    lateinit var userAddPhoneNum: EditText
    lateinit var createUserBtn: Button


    lateinit var viewmodel: UsersViewModel

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_user)

        viewmodel = ViewModelProvider(this).get(UsersViewModel::class.java)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        viewmodel.userNumbers().observe(this, Observer {
            Log.d("NUMBERTOTAL", "${it}")
            index = it
        })

        userAddName = findViewById(R.id.etName)
        userAddUsername = findViewById(R.id.etUsername)
        userAddEmail = findViewById(R.id.etEmail)
        userAddPhoneNum = findViewById(R.id.etPhone)
        createUserBtn = findViewById(R.id.btnregister)

        createUserBtn.setOnClickListener {
            saveUser()
        }
    }

    fun saveUser(){
        insertingNewUserInDB()
        val data = Intent()
        setResult(Activity.RESULT_OK, data)
        finish()
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down)
    }


    fun insertingNewUserInDB(){

        val users = Users()
        users.id = index + 1
        users.name = userAddName.text.toString()
        users.email = userAddEmail.text.toString()
        users.username =  userAddUsername.text.toString()
        users.phone = userAddPhoneNum.text.toString()
        viewmodel.insertResponse(users)

    }


}
