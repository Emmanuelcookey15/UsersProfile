package com.LineCard.userprofileprojectquestionone.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(users: Users)

    @Query("SELECT * FROM users_table WHERE id LIKE :id ORDER BY id")
    fun selectUser(id: Int): LiveData<Users>

    @Query("UPDATE users_table set image=:image where id=:id")
    fun updateUser(id: Int, image: String)

    // /** get all product **/
    @Query("SELECT * FROM  users_table")
    fun getAllUsers(): LiveData<List<Users>>

}