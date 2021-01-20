package com.LineCard.userprofileprojectquestionone.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
class Users {

    @PrimaryKey
    var id: Int?=null
    var name: String=""
    var username: String=""
    var email: String=""
    var phone: String=""
    var image: String=""

}