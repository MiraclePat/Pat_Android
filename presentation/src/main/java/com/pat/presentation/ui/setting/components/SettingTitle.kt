package com.pat.presentation.ui.setting.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Typography

@Composable
fun SettingTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Gray500
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text, style = Typography.labelMedium, color = color, fontSize = 12.sp)
    }
}