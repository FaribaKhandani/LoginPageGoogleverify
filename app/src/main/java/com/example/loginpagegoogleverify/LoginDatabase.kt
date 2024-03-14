package com.example.loginpagegoogleverify

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LoginEntity::class],version = 1)
abstract class LoginDatabase: RoomDatabase() {

    abstract val daoLogin:DaoLogin


    companion object{

        private var INSTANCE:LoginDatabase? =null
        fun getInstance(context: Context):LoginDatabase{

            synchronized(this){
                var instance :LoginDatabase ?= INSTANCE

                if(instance ==null){

                    instance =
                        Room.databaseBuilder(context.applicationContext,LoginDatabase::class.java,"user_database").allowMainThreadQueries().build()

                }

                return instance
            }
        }
    }

}