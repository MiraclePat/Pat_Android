package com.pat.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.pat.presentation.ui.navigations.BottomNavi
import com.pat.presentation.ui.navigations.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

//            GlideImage(
//                imageModel = { "https://miraclepatbucket.s3.ap-northeast-2.amazonaws.com/repimgtest.JPG" },
//            )
            Scaffold(
                bottomBar = { BottomNavi(navController = navController) }
            ) {
                Box(Modifier.padding(it)) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }

}