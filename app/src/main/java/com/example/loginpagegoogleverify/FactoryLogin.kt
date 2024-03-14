package com.example.loginpagegoogleverify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FactoryLogin(val repositoryLogin: RepositoryLogin): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ViewModelLogin::class.java)){

            return ViewModelLogin(repositoryLogin) as T
        }
        throw IllegalArgumentException("unknown viewmodel class ")

    }
}