package com.pat.presentation.ui.map

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MapScreenView() {
    val scrollState = rememberScrollState()
    Surface {
        Text(text = "Hello, Map")
    }
}

