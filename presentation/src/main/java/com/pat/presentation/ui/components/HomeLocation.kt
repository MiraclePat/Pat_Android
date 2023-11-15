package com.pat.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.screens.HomeScreenView
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.White


@Composable
fun LocationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = MaterialTheme.colorScheme.background,
    text: String = "관악구",
    textColor: Color = Gray600,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {

    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) onPressing()
        else onPressed()
    }

    Button(
        modifier = modifier
            .height(40.dp)
            .width(66.dp),
        interactionSource = interactionSource,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        onClick = {
            onClick()
        },
    ) {
        Row(modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor,
                overflow = TextOverflow.Visible

            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint = Gray700
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview3() {
    HomeScreenView()
}