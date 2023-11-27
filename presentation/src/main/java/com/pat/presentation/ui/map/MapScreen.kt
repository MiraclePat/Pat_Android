package com.pat.presentation.ui.map

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pat.presentation.ui.pat.PattingViewModel
import com.pat.presentation.ui.proof.PattingScreenView

@Composable
fun MapScreenView(navController : NavController, viewModel: PattingViewModel) {
    val scrollState = rememberScrollState()


    PattingScreenView(navController=navController, viewModel = viewModel)
}

