package com.pat.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.pat.presentation.R
import com.pat.presentation.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        repeatOnStarted(this) {
            viewModel.splashEvent.collectLatest {
                val isLogin = when(it) {
                    SplashEvent.LoginSuccess -> {
                        "SUCCESS"
                    }

                    SplashEvent.LoginFailed -> {
                        "FAILED"
                    }
                }
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.putExtra("IS_LOGIN", isLogin)
                startActivity(intent)
                finish()
            }
        }
    }
}