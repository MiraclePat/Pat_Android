package com.pat.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray50


@Composable
fun Divider(modifier: Modifier = Modifier, color: Color = Gray50) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(color)
    )
}