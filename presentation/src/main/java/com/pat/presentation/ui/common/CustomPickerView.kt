package com.pat.presentation.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography


@Composable
fun CustomPicker(
    modifier: Modifier = Modifier,
    text: String,
    dateState: MutableState<String>,
    formatter: (String) -> String,
    widthSize: Dp = 85.dp,
    content: @Composable () -> Unit,
    clickState: MutableState<Boolean>
) {
    val borderColor = if (dateState.value.isEmpty()) Gray300 else PrimaryMain
    val width = if (dateState.value.isEmpty()) widthSize else 85.dp

    if (clickState.value) {
        content()
    }
    CustomPickerView(
        text = text,
        dateState = dateState,
        formatter = formatter,
        widthSize = width,
        borderColor = borderColor
    ) {
        clickState.value = true
    }
}

@Composable
fun CustomPickerView(
    modifier: Modifier = Modifier,
    text: String,
    dateState: MutableState<String>,
    formatter: (String) -> String,
    widthSize: Dp,
    borderColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(widthSize)
            .height(36.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(1.dp, color = borderColor, shape = RoundedCornerShape(100.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (dateState.value.isEmpty()) {
            Text(
                text = text,
                style = Typography.labelMedium,
                color = Gray600,
            )
        } else {
            Text(
                text = formatter(dateState.value),
                style = Typography.labelMedium,
                color = PrimaryMain,
            )
        }
    }
}