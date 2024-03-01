package com.example.loginpagegoogleverify

class RepositoryLogin(val daoLogin: DaoLogin) {

    val usersLogin =daoLogin.getAll()

    suspend fun insertLogin(entity: LoginEntity){
        daoLogin.insert(entity)
    }

    suspend fun  updateLogin(entity: LoginEntity){
        daoLogin.update(entity)
    }

    suspend fun  VerifyUsernameAndPassword(username:String,password:String):LoginEntity?{

        return daoLogin.VerifyUserPassword(username,password)
    }

    suspend fun lastItem():LoginEntity?{
        return daoLogin.getlastitems()
    }

    fun emailCheck():List<LoginEntity>{
        return daoLogin.getEmail()
    }


    suspend fun isEmailExists(email: String): Boolean {
        return daoLogin.countByEmail(email) > 0
    }
}