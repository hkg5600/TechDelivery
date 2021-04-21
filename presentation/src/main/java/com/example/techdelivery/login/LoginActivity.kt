package com.example.techdelivery.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo.IME_ACTION_SEND
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.core.utils.EventObserver
import com.example.techdelivery.main.MainActivity
import com.example.techdelivery.R
import com.example.techdelivery.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthCredential
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.editTextEmail.setOnEditorActionListener { v, actionId, event ->
            if (actionId == IME_ACTION_SEND) {
                viewModel.startLoginFlow()
                return@setOnEditorActionListener false
            }

            return@setOnEditorActionListener true
        }

        viewModel.reLogin.observe(this, {
            if (it) {
                firebaseReLogin()
            }
        })

        viewModel.startLoginFlow.observe(this, EventObserver {
            firebaseLogin(it)
        })

        viewModel.navigateToMain.observe(this, EventObserver {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        viewModel.loginError.observe(this, EventObserver {
            showSnackbar("로그인 중 오류가 발생하였습니다. (code : $it)", Snackbar.LENGTH_SHORT)
        })

        viewModel.emailIsNotValid.observe(this, EventObserver {
            showSnackbar("이메일 형식이 올바르지 않습니다.", Snackbar.LENGTH_SHORT)
        })

        viewModel.loading.observe(this) { isLoading ->
            if (!isLoading) return@observe
            //showSnackbar("로그인 중...", Snackbar.LENGTH_INDEFINITE)
        }

    }

    private fun firebaseReLogin() {
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        val provider = OAuthProvider.newBuilder("github.com")

        if (firebaseUser == null) {
            showSnackbar("기존 로그인 정보를 불러오는데 실패했습니다.", Snackbar.LENGTH_SHORT)
            return
        }

        firebaseUser
            .startActivityForReauthenticateWithProvider(this, provider.build())
            .addOnSuccessListener {

                val credential = it.credential as OAuthCredential
                
                login(credential.accessToken)

            }
            .addOnFailureListener {
                showSnackbar("기존 로그인 정보를 불러오는데 실패했습니다.", Snackbar.LENGTH_SHORT)
            }
    }

    private fun firebaseLogin(email: String) {
        val provider = OAuthProvider.newBuilder("github.com").apply {
            addCustomParameter("login", email)
        }

        Firebase.auth
            .startActivityForSignInWithProvider( /* activity= */this, provider.build())
            .addOnSuccessListener {
                it.user?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
                    login(tokenResult.token)
                }
            }
            .addOnFailureListener {
                showSnackbar("깃허브 연동에 실패하였습니다.", Snackbar.LENGTH_SHORT)
            }
    }

    private fun login(token: String?) {
        if (token.isNullOrBlank()) {
            showSnackbar("깃허브 연동에 실패하였습니다.", Snackbar.LENGTH_SHORT)
            return
        }

        viewModel.login(token)  //Login with this token
    }

    private val snackbar by lazy {
        Snackbar.make(binding.holderLayout, "", Snackbar.LENGTH_SHORT)
    }

    private fun showSnackbar(message: String, mode: Int) {
        snackbar.dismiss()
        snackbar.setText(message)
        snackbar.duration = mode
        snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snackbar.show()
    }

}
