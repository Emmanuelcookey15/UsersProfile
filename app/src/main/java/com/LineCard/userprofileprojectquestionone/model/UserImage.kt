package com.LineCard.userprofileprojectquestionone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_image")
class UserImage {

    @PrimaryKey
    var id: Int?=null
    var image: String=""
}