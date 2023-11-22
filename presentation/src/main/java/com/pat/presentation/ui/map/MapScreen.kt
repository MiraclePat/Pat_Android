package com.pat.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pat.presentation.ui.pat.PattingScreen
import com.pat.presentation.ui.pat.PattingScreenView
import com.pat.presentation.ui.post.PostScreenView

@Composable
fun MapScreenView(navController : NavController) {
    val scrollState = rememberScrollState()


    PattingScreenView(navController=navController)
}

