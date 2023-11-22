package com.pat.presentation.ui.map

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import com.pat.presentation.ui.proof.PattingScreenView

@Composable
fun MapScreenView() {
    val scrollState = rememberScrollState()


    PattingScreenView()
}

