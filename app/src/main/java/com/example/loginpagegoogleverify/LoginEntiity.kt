package com.example.loginpagegoogleverify

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logintable")

data class LoginEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,


    @ColumnInfo(name = "username")
    var username:String,

    @ColumnInfo(name = "password")
    var password:String,

    @ColumnInfo(name = "firstname")
    var firstname:String,

    @ColumnInfo(name = "lastname")
    var lastname:String


)