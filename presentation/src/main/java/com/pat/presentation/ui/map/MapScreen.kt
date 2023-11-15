package com.pat.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun MapScreenView() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            model = "https://miraclepatbucket.s3.ap-northeast-2.amazonaws.com/repimgtest.JPG",
            contentDescription = null
        )
    }
}

