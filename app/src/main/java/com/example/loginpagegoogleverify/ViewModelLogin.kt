package com.example.loginpagegoogleverify

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Observable
import java.util.regex.Pattern

class ViewModelLogin(val repositoryLogin: RepositoryLogin) : ViewModel(), androidx.databinding.Observable{


    val users = repositoryLogin.usersLogin

    val userlist = repositoryLogin.emailCheck()




    private val _loginResult = MutableLiveData<Boolean>()
    val loginResultt: LiveData<Boolean> get() = _loginResult



    private val _lastInsertedItem = MutableLiveData<LoginEntity?>()
    val lastInsertedItem: LiveData<LoginEntity?> = _lastInsertedItem



    val emailResult = MutableLiveData<Boolean>()
    val passwordResult = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()



    fun ilastitems (){
        viewModelScope.launch {
            _lastInsertedItem.value = repositoryLogin.lastItem()
        }
    }

    @Bindable
    val username = MutableLiveData<String>()

    @Bindable
    val password = MutableLiveData<String>()

    @Bindable
    val firstname = MutableLiveData<String>()

    @Bindable
    val lastname = MutableLiveData<String>()




    fun loginButton() {

        if (username.value == null || password.value == null) {

            loginResult.value = false
        } else {
            CoroutineScope(Dispatchers.Main).launch {


                var user =
                    repositoryLogin.VerifyUsernameAndPassword(username.value!!, password.value!!)

                if (user?.password != password.value) {
                    loginResult.value = false

                } else if (user!!.username == username.value || user!!.password == password.value) {

                    loginResult.value = true
                }


            }


        }
    }




    fun registerButton():Boolean {

        if (firstname.value == null || lastname.value == null || username.value == null || password.value == null) {

        }
        else
        {
            val passwordm = password.value!!
            val usernamem = username.value!!
            val firstnamem = firstname.value!!
            val lastnamem = lastname.value!!





            if (validEmail(usernamem) == true && validPassword(passwordm) == true) {

                insertM(LoginEntity(0, usernamem, passwordm, firstnamem, lastnamem))

                return true

            }


        }
        return false

    }






    fun validEmail(email: String): Boolean {


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailResult.value = false
            return false
        }
        emailResult.value = true
        return true

    }



    fun validPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"


        val pattern = Pattern.compile(passwordPattern)
        if (!pattern.matcher(password).matches()) {
            passwordResult.value = false
            return false

        }
        passwordResult.value = true
        return true
    }



    fun insertM(entity: LoginEntity)  {
        viewModelScope.launch {
            repositoryLogin.insertLogin(entity)
        }
    }

    override fun addOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }


}