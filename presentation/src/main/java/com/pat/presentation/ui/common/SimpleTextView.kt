package com.pat.presentation.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Typography

@Composable
fun SimpleTextView(
    modifier: Modifier = Modifier,
    text: String,
    iconSize: Dp = 12.dp,
    vectorResource: Int,
    style: TextStyle = Typography.labelSmall,
    spacePadding: Dp = 4.dp,
    iconColor: Color = Gray400
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = modifier.size(iconSize),
            imageVector = ImageVector.vectorResource(id = vectorResource),
            contentDescription = null,
            tint = iconColor
        )
        Spacer(Modifier.size(spacePadding))
        Text(text = text, style = style)
    }
}