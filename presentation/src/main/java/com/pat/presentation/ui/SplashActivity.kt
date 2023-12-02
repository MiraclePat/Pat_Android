package com.pat.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pat.presentation.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            // 앱의 MainActivity로 넘어가기
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            // 현재 액티비티 닫기
            finish()
        }, 2000) // 3초
    }
}