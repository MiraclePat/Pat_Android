package com.pat.presentation.ui.home.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.home.HomeScreenView
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
fun CategoryButtonList(

) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val categories = listOf<String>("전체", "환경", "건강", "식습관", "취미", "생활", "기타")
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    categories.forEach { category ->
        CategoryButton(
            text = category,
            onClick = { selectedCategory = category },
            isSelected = selectedCategory == category
        )
        Spacer(Modifier.size(10.dp))
    }
}


@Composable
fun CategoryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isSelected: Boolean = false
) {
    val habitWidth = if (text == "식습관") 58.dp else 47.dp

    val buttonColor = if (isSelected) Primary50 else White
    val textColor = if (isSelected) PrimaryMain else Gray500
    val border = if (isSelected) PrimaryMain else Gray500
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