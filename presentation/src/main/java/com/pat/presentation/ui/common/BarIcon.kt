package com.pat.presentation.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray700

@Composable
fun BarIcon(
    modifier: Modifier = Modifier,
    onclick: () -> Unit = {},
    source: Int,
    contentDescription: String? = null,
    tint: Color = Gray700
) {
    IconButton(modifier = modifier.size(32.dp), onClick = onclick) {
        Icon(
            painter = painterResource(id = source),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}