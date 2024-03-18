package com.example.loginpagegoogleverify

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginpagegoogleverify.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    lateinit var registerBinding: FragmentRegisterBinding
    lateinit var viewModelLogin: ViewModelLogin

    var state = false
    var matchFound = false

    val emailVerify = MutableLiveData<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val dao = LoginDatabase.getInstance(requireContext()).daoLogin
        val repository = RepositoryLogin(dao)
        val factory = FactoryLogin(repository)




        registerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)




        viewModelLogin = ViewModelProvider(this, factory).get(ViewModelLogin::class.java)
        registerBinding.mvvmregister = viewModelLogin





        registerBinding.bottonregister.setOnClickListener {

            var userss = viewModelLogin.userlist
            matchFound = false
            Log.e("users8",userss.size.toString())
            for(i in userss.indices){


                if(userss[i].username.toString() == registerBinding.frgmntregEtUsername.text.toString()){
                    Log.e("users9", userss[i].username)
                    matchFound = true
                    break
                }
            }



            emailVerify.value = !matchFound

            Log.e("users5",emailVerify.value.toString())

            if (viewModelLogin.registerButton() == true && emailVerify.value ==true) {



                val sharedPreferences = requireActivity().getSharedPreferences(
                    "MyPreferences",
                    Context.MODE_PRIVATE
                )
                val editor = sharedPreferences.edit()
                var preferenceValue = registerBinding.frgmntregEtUsername.text.toString()

                editor.putString("emailAddress", preferenceValue.toString());
                editor.apply();
                val intent = Intent(activity, BlankFragment::class.java)
                startActivity(intent)






            }

            else
            {
                Toast.makeText(requireContext(), "Email Address or Password is invalid", Toast.LENGTH_LONG).show()




            }



        }




        viewModelLogin.emailResult.observe(requireActivity(), Observer {

            if (it == true) {

                registerBinding.frgmntregTvUsercheck.visibility = View.VISIBLE
                registerBinding.frgmntregTvUsercheck.hint = "Valid email address"
                registerBinding.frgmntregTvUsercheck.setHintTextColor(Color.GREEN)


            } else {
                registerBinding.frgmntregTvUsercheck.visibility = View.VISIBLE
                registerBinding.frgmntregTvUsercheck.hint = "Invalid email address"
                registerBinding.frgmntregTvUsercheck.setHintTextColor(Color.RED)
            }
        })


        viewModelLogin.passwordResult.observe(requireActivity(), Observer {
            if (it == true) {
                registerBinding.frgmntregTvPasscheck.visibility = View.VISIBLE
                registerBinding.frgmntregTvPasscheck.hint = "Valid Password"
                registerBinding.frgmntregTvPasscheck.setHintTextColor(Color.GREEN)


            } else {
                registerBinding.frgmntregTvPasscheck.visibility = View.VISIBLE
                registerBinding.frgmntregTvPasscheck.hint =
                    "Minimum 8 characters,Uppercase\n Lowercase,numeric and special character"
                registerBinding.frgmntregTvPasscheck.setHintTextColor(Color.RED)
            }
        })


        darkmodeCheck()
        return registerBinding.root
    }






    fun darkmodeCheck(){
        val isDarkMode = isDarkModeEnabled(requireContext())
        if (isDarkMode) {

            registerBinding.frgmntregTvFirstname.setTextColor(android.graphics.Color.WHITE)
            registerBinding.frgmntregTvLastname.setTextColor(android.graphics.Color.WHITE)
            registerBinding.frgmntregTvEmailadd.setTextColor(android.graphics.Color.WHITE)
            registerBinding.frgmntregTvPassword.setTextColor(android.graphics.Color.WHITE)
            registerBinding.frgmntregTvSignup.setTextColor(android.graphics.Color.WHITE)
            registerBinding.frgmntregEtUsername.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregEtPassword.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregEtLastname.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregEtFirstname.setTextColor(android.graphics.Color.BLACK)

        } else {

            registerBinding.frgmntregTvFirstname.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregTvLastname.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregTvEmailadd.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregTvPassword.setTextColor(android.graphics.Color.BLACK)
            registerBinding.frgmntregTvSignup.setTextColor(android.graphics.Color.BLACK)
        }
    }

    fun isDarkModeEnabled(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK

        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }



}