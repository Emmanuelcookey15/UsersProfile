package com.LineCard.userprofileprojectquestionone.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LineCard.userprofileprojectquestionone.model.UserImage

@Dao
interface UserImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(userImage: UserImage)

    @Query("UPDATE user_image set image=:image where id=:id")
    fun updateUserImage(id: Int, image: String)

    @Query("SELECT * FROM user_image WHERE id LIKE :id ORDER BY id")
    fun selectImage(id: Int): LiveData<UserImage>

}