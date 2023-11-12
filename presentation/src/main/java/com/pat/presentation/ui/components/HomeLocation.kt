package com.pat.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun LocationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = Color.White,
    text: String,
    textColor: Color = Color.Black,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {

    val isPressed by interactionSource.collectIsPressedAsState()

    val verticalPadding by animateDpAsState(
        targetValue = if (isPressed) 13.dp else 16.dp, label = ""
    )

    LaunchedEffect(isPressed) {
        if (isPressed) onPressing()
        else onPressed()
    }

    Button(
        modifier = modifier,
        interactionSource = interactionSource,
        contentPadding = PaddingValues(vertical = verticalPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        onClick = {
            onClick()
        },
    ) {
        Row() {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyMedium,
                text = text,
                color = textColor
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}