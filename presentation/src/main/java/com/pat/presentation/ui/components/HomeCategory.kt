package com.pat.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.screens.HomeScreenView
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.White


@Composable
fun HomeCategory(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Row(
        modifier
            .padding(horizontal = 16.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LocationButton()
        Row(
            modifier
                .padding(8.dp)
                .horizontalScroll(scrollState)
        ) {
            CategoryButtonList()
        }
    }
}


@Composable
fun CategoryButtonList() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonColor: Color


    val categories = listOf<String>("전체", "환경", "건강", "식습관", "취미", "생활")
    categories.forEach { category ->
        if (isPressed) {
            CategoryButton(
                text = category,
                textColor = PrimaryMain,
                border = PrimaryMain,
                buttonColor = Primary50
            )
        } else {
            CategoryButton(text = category)
        }
        Spacer(Modifier.size(10.dp))
    }
}


@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonColor: Color = White,
    text: String,
    textColor: Color = Gray500,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    border: Color = Gray500,
    onPressing: () -> Unit = {},
    onPressed: () -> Unit = {}
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val habitWidth = if (text == "식습관") 58.dp else 47.dp

//    LaunchedEffect(isPressed) {
//        if (isPressed) onPressing()
//        else onPressed()
//    }

    Button(
        modifier = modifier
            .requiredHeight(32.dp)
            .requiredWidth(habitWidth),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(22.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, border),
        onClick = {
            onClick()
        },
    ) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview2() {
    HomeScreenView()
}