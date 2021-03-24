package com.example.techdelivery.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.data.session.FirebaseUserSession
import com.example.domain.session.LogoutUseCase
import com.example.techdelivery.R
import com.example.techdelivery.databinding.ActivityLoginBinding
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    @Inject lateinit var test : FirebaseUserSession
    @Inject lateinit var usecase : LogoutUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        lifecycleScope.launchWhenResumed {
            usecase(Unit).collect {
                it
            }
        }
        //Todo Add Github Login
    }

    fun test() {
        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("login", "hkg5600@gmail.com");
        val firebaseAuth = Firebase.auth
        firebaseAuth
            .startActivityForSignInWithProvider( /* activity= */this, provider.build())
            .addOnSuccessListener {
                it.user?.getIdToken(true)?.addOnSuccessListener {
                    it.token
                }
            }
            .addOnFailureListener {
                Log.e("Error", it.message ?: "")
                // Handle failure.
            }
    }

}
