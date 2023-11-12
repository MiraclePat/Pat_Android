package com.pat.presentation.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun HomeCategory(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Row(modifier.padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        LocationButton(text = "관악구")
        Spacer(Modifier.size(10.dp))
        Row(
            modifier
                .padding(10.dp)
                .horizontalScroll(scrollState)
        ) {
            CategoryButtonList()
        }
    }
}


@Composable
fun CategoryButtonList() {
    val categories = listOf<String>("전체", "환경", "건강", "식습관", "취미", "생활")
    categories.forEach { category ->
        CategoryButton(text = category)
        Spacer(Modifier.size(10.dp))
    }
}


@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = Color.Black,
    text: String,
    textColor: Color = Color.White,
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
        modifier = modifier.height(45.dp),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(22.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        onClick = {
            onClick()
        },
    ) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            color = textColor
        )
    }
}