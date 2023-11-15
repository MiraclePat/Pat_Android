package com.pat.presentation.ui.proof

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.pat.presentation.ui.pat.PostDetailView

@Composable
fun CertificationScreenView() {
    val scrollState = rememberScrollState()
    Surface {
        PostDetailView()
    }
}

