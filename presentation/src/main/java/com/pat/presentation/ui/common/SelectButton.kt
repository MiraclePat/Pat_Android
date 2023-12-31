package com.pat.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

@Composable
fun SelectButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    backColor: Color = PrimaryMain,
    textColor: Color = White,
    stokeColor: Color = PrimaryMain,
    stokeWidth: Dp = 0.dp,
    cornerSize: Dp = 0.dp
) {
    Box(
        modifier
            .width(320.dp)
            .height(46.dp)
            .clip(RoundedCornerShape(cornerSize))
            .background(backColor)
            .border(
                BorderStroke(stokeWidth, stokeColor),
                shape = RoundedCornerShape(cornerSize)
            )
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Text(
            text, style = Typography.labelMedium, color = textColor
        )
    }
}