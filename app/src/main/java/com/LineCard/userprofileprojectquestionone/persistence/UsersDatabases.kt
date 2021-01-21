package com.LineCard.userprofileprojectquestionone.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.LineCard.userprofileprojectquestionone.model.UserImage
import com.LineCard.userprofileprojectquestionone.model.Users


@Database(entities = [Users::class, UserImage::class],version = 1)
abstract class UsersDatabases: RoomDatabase() {


    abstract  fun usersDao(): UsersDao
    abstract  fun usersImageDao(): UserImageDao

    companion object {

        @Volatile
        private var INSTANCE: UsersDatabases? = null

        fun getDatabase(context: Context): UsersDatabases {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabases::class.java,
                        "Users_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }


    }


}