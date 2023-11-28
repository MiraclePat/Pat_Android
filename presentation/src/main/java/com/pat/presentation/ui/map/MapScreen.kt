package com.pat.presentation.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreenView() {
    val scrollState = rememberScrollState()


//    ProofScreenView()
    NaverMap(
        modifier = Modifier.fillMaxSize()
    )
}

