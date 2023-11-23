package com.pat.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White


@Composable
fun CustomButtonView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    shape: Shape,
    fontSize: TextUnit,
    borderColor: Color,
    textColor : Color
) {
    val selectedButtonColor = if (isSelected) Primary50 else White
    val selectedTextColor = if (isSelected) PrimaryMain else textColor
    val border = if (isSelected) PrimaryMain else borderColor

    Button(
        modifier = modifier,
        shape = shape,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = selectedButtonColor
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, border),
        onClick = {
            onClick()
        },
    ) {
        Text(
            style = Typography.displaySmall,
            text = text,
            fontSize = fontSize,
            color = selectedTextColor
        )
    }
}