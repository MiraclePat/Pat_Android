package com.pat.presentation.ui.screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CertificationScreenView() {
    val scrollState = rememberScrollState()
    Surface {
        PostDetailView()
    }
}

