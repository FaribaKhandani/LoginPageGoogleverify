package com.example.loginpagegoogleverify

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