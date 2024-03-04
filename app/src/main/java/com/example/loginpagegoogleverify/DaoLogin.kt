package com.example.loginpagegoogleverify

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoLogin {


    @Insert
    suspend fun  insert(loginEntity:LoginEntity)

    @Update
    suspend fun  update(loginEntity: LoginEntity)

    @Query("SELECT*FROM logintable")
    fun  getAll(): LiveData<List<LoginEntity>>

    @Query("SELECT*FROM logintable WHERE username LIKE :userName AND password LIKE :passWord")
    suspend fun VerifyUserPassword(userName:String,passWord:String):LoginEntity?

    @Query("SELECT * FROM logintable ORDER BY id DESC LIMIT 1")
    suspend fun getlastitems():LoginEntity?


    @Query("SELECT*FROM logintable ")
    fun getEmail(): List<LoginEntity>

    @Query("SELECT COUNT(*) FROM logintable WHERE username = :username")
    suspend fun countByEmail(username: String): Int

}