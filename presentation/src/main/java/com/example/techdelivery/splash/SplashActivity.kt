package com.example.techdelivery.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.techdelivery.R
import com.example.techdelivery.databinding.ActivitySplashBinding
import com.example.techdelivery.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        //Todo Add token verify

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}