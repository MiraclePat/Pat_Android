package com.pat.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.identity.android.legacy.Utility
import com.kakao.sdk.common.KakaoSdk
import com.pat.presentation.BuildConfig
import com.pat.presentation.ui.login.LoginScreenView
import com.pat.presentation.ui.navigations.BottomNavi
import com.pat.presentation.ui.navigations.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isAutoLogin = intent.getStringExtra("IS_LOGIN")
        Log.e("custom", "$isAutoLogin")

        if (!hasRequiredPermissions()) {   //TODO USECASE로 빼기
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        setContent {
            val navController = rememberNavController()
//            val isLogin = remember { mutableStateOf(false) }

            Scaffold(
                bottomBar = { BottomNavi(navController = navController) }
            ) {
                Box(Modifier.padding(it)) {
                    NavigationGraph(
                        navController = navController,
                        isAutoLogin = isAutoLogin.toString()
                    )
                }
            }

        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

}