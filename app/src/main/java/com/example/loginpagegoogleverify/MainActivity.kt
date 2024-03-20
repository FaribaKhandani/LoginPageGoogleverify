package com.example.loginpagegoogleverify

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loginpagegoogleverify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    lateinit var viewModelLogin: ViewModelLogin
    override fun onCreate(savedInstanceState: Bundle?) {

        val dao = LoginDatabase.getInstance(this).daoLogin
        val repository = RepositoryLogin(dao)
        val factory = FactoryLogin(repository)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModelLogin = ViewModelProvider(this, factory).get(ViewModelLogin::class.java)


        val isDarkMode = isDarkModeEnabled(this)
        if (isDarkMode) {
            setTheme(R.style.AppTheme_Dark)
        } else {

            setTheme(R.style.AppTheme_Light)
        }

        super.onCreate(savedInstanceState)




        val sharedPreferencSignout: SharedPreferences = getSharedPreferences("MyPreferencesSignout", MODE_PRIVATE)
        val userSignout = sharedPreferencSignout.getBoolean("userSignout",false)

        val sharedPreferencActivityNews: SharedPreferences = getSharedPreferences("ActivityStatus", MODE_PRIVATE)
        val userActivityNews = sharedPreferencActivityNews.getBoolean("isActivityOpened",false)

        if  ( userActivityNews && !userSignout) {


            Log.e("uservalid", userActivityNews.toString())
            Log.e("usersign", userSignout.toString())
            val intent = Intent(this, BlankFragment::class.java)
            startActivity(intent)
            finish()
        } else {



            val loginFragment = LoginFragment()
            addFragment(loginFragment)
        }



    }


    fun isDarkModeEnabled(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK

        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    fun addFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainerV, fragment).commit()


    }
}