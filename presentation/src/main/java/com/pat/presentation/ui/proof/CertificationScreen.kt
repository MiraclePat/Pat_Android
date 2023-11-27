package com.pat.presentation.ui.proof

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.pat.presentation.ui.pat.PatDetailView

@Composable
fun CertificationScreenView(navController: NavController) {
    val scrollState = rememberScrollState()
    Surface {
        PatDetailView(navController)
    }
}

