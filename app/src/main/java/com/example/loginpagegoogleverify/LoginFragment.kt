package com.example.loginpagegoogleverify

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginpagegoogleverify.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.mortbay.thread.Timeout


class LoginFragment : Fragment() {
    lateinit var loginBinding: FragmentLoginBinding
    lateinit var clickableSpan: ClickableSpan
    lateinit var viewModelLogin: ViewModelLogin

    var userState = false

    lateinit var googleSignInOptions: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        const val RC_SIGN_IN = 9964
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dao = LoginDatabase.getInstance(requireContext()).daoLogin
        val repository = RepositoryLogin(dao)
        val factory = FactoryLogin(repository)


        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        viewModelLogin = ViewModelProvider(this, factory).get(ViewModelLogin::class.java)
        loginBinding.mvvmlogin = viewModelLogin

        loginCheck()

        googleSignIn()


        loginBinding.frgmntloginImageGmaillogin.setOnClickListener {

            singIn()

        }



        darkmodeCheck()
        MoveToSignUp()

        return loginBinding.root
    }

    fun darkmodeCheck(){
        val isDarkMode = isDarkModeEnabled(requireContext())
        if (isDarkMode) {

            loginBinding.frgmntloginTvEmailaddress.setTextColor(android.graphics.Color.WHITE)
            loginBinding.frgmntloginTvPassword.setTextColor(android.graphics.Color.WHITE)
            loginBinding.frgmntloginTvSignin.setTextColor(android.graphics.Color.WHITE)
            loginBinding.frgmntloginTvLoginwith.setTextColor(android.graphics.Color.WHITE)
            loginBinding.frgmntloginEtEmail.setTextColor(android.graphics.Color.BLACK)
            loginBinding.frgmntloginEtPassword.setTextColor(android.graphics.Color.BLACK)


        } else {

            loginBinding.frgmntloginTvEmailaddress.setTextColor(android.graphics.Color.BLACK)
            loginBinding.frgmntloginTvPassword.setTextColor(android.graphics.Color.BLACK)
            loginBinding.frgmntloginTvSignin.setTextColor(android.graphics.Color.BLACK)
            loginBinding.frgmntloginTvLoginwith.setTextColor(android.graphics.Color.BLACK)
            loginBinding.frgmntloginEtEmail.setTextColor(android.graphics.Color.BLACK)
            loginBinding.frgmntloginEtPassword.setTextColor(android.graphics.Color.BLACK)
        }
    }

    fun isDarkModeEnabled(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK

        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
    private fun loginCheck(){
        viewModelLogin.loginResult.observe(requireActivity(), Observer {
            if (it == true) {
                val sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                var s:String = loginBinding.frgmntloginEtEmail.text.toString()
                // Log.e("dd",s.toString())
                editor.putString("emailAddress", s.toString());
                editor.apply();
               // val intent = Intent(activity, ActivityNews::class.java)
               // startActivity(intent)
                userState = true

                val sharedPreferencesuser = requireActivity().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
                val editoruser = sharedPreferencesuser.edit()
                // editoruser.putBoolean("userValid",userState)
                editor.apply()

            } else {
                loginBinding.frgmntloginTvWarning.visibility = View.VISIBLE
                loginBinding.frgmntloginTvWarning.text = "The Email or Password is incorrect"
            }
        })
    }
    private fun googleSignIn(){
        googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
    }

    private fun singIn() {
        val intent = googleSignInClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Timeout.Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            handleSignIn(task)

        }
    }


    private fun handleSignIn(completedTask: Timeout.Task<GoogleSignInAccount>) {

        completedTask.getResult(ApiException::class.java)
        Toast.makeText(activity,"successful", Toast.LENGTH_LONG).show()
       // val intent = Intent(activity, ActivityNews::class.java)
      //  startActivity(intent)

        // val transaction = requireActivity().supportFragmentManager.beginTransaction()
        // transaction.replace(R.id.fragmentContainerV, newsFragment)
        //  transaction.commit()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity.finishAffinity()
            }
        }
        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    fun MoveToSignUp() {
        val text = "Not registered yes? Sing up!"
        val spannableString = SpannableString(text)

        clickableSpan = object : ClickableSpan() {

            override fun onClick(p0: View) {

                val registerFragment = RegisterFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerV, registerFragment)
                transaction.commit()

            }
        }

        spannableString.setSpan(clickableSpan, 20, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        loginBinding.frgmntloginTvSignup.setText(spannableString, TextView.BufferType.SPANNABLE)
        loginBinding.frgmntloginTvSignup.movementMethod = LinkMovementMethod.getInstance()
    }


}