package com.LineCard.userprofileprojectquestionone.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Users::class],version = 1)
abstract class UsersDatabases: RoomDatabase() {


    abstract  fun usersDao(): UsersDao

    companion object {

        @Volatile
        private var INSTANCE: UsersDatabases? = null

        fun getDatabase(context: Context): UsersDatabases {
            synchronized(this) {
                var instance = INSTANCE

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