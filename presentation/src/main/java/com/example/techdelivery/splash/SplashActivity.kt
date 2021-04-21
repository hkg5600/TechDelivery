package com.example.techdelivery.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.core.utils.EventObserver
import com.example.techdelivery.main.MainActivity
import com.example.techdelivery.R
import com.example.techdelivery.databinding.ActivitySplashBinding
import com.example.techdelivery.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    private val viewModel : SplashViewModel by viewModels()

    private val errorDialog by lazy {
       MaterialAlertDialogBuilder(this)
            .setMessage("실행 중 문제가 발생하였습니다.")
            .setPositiveButton("종료") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
           .create()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        //Todo Add token verifyp

        viewModel.error.observe(this, EventObserver {
            errorDialog.show()
        })

        viewModel.navigateToMain.observe(this, {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        })

        viewModel.navigateToLogin.observe(this, EventObserver { reLogin ->
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("RE_LOGIN", reLogin)
            }
            startActivity(intent)
            finish()
        })
    }
}