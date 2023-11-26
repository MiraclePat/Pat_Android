package com.pat.presentation.ui.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.theme.PrimaryMain

val setUnderLine = Modifier.drawBehind {
    val strokeWidthPx = 1.dp.toPx()
    val verticalOffset = size.height - 2.sp.toPx()
    drawLine(
        color = PrimaryMain,
        strokeWidth = strokeWidthPx,
        start = Offset(0f, verticalOffset),
        end = Offset(size.width, verticalOffset)
    )
}