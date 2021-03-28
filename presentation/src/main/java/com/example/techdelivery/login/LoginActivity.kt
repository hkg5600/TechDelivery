package com.example.techdelivery.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.techdelivery.R
import com.example.techdelivery.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private val firebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel.reLogin.observe(this, {
            if (it) {
                firebaseReLogin()
            } else {
                firebaseLogin()
            }
        })

    }

    private fun firebaseReLogin() {
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        val provider = OAuthProvider.newBuilder("github.com")
        firebaseUser
            ?.startActivityForReauthenticateWithProvider(this, provider.build())
            ?.addOnSuccessListener {
               it.user?.getIdToken(true)?.addOnSuccessListener {  tokenResult ->
                   tokenResult.token //Login with this token
               }
            }
            ?.addOnFailureListener {
                Snackbar.make(binding.holderLayout, "기존 로그인 정보를 불러오는데 실패했습니다.", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun firebaseLogin() {
        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("login", "hkg5600@gmail.com");
        val firebaseAuth = Firebase.auth
        firebaseAuth
            .startActivityForSignInWithProvider( /* activity= */this, provider.build())
            .addOnSuccessListener {
                it.user?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
                   tokenResult.token  //Login with this token
                }
            }
            .addOnFailureListener {
                Snackbar.make(binding.holderLayout, "로그인에 실패했습니다.", Snackbar.LENGTH_SHORT).show()
            }
    }

}
