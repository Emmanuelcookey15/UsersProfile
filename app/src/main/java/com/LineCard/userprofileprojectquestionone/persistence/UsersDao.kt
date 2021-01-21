package com.LineCard.userprofileprojectquestionone.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LineCard.userprofileprojectquestionone.model.Users

@Dao
interface UsersDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(users: Users)

    @Query("SELECT * FROM users_table WHERE id LIKE :id ORDER BY id")
    fun selectUser(id: Int): LiveData<Users>

    // /** get all product **/
    @Query("SELECT * FROM  users_table")
    fun getAllUsers(): LiveData<List<Users>>

    @Query("SELECT COUNT(*) from users_table")
    fun userCount() : LiveData<Int>

}