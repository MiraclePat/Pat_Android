package com.pat.presentation.ui.common

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pat.presentation.ui.theme.Typography

@Composable
fun ExampleImageView(
    navController: NavController,
    modifier: Modifier = Modifier,
    backColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit = {},
    hasSource: String = "",
) {
    Column(modifier.clickable {
        onClick()
    }) {
//        SelectImage(navController = navController)
        Box(
            modifier
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(backColor)
                .height(26.dp)
                .width(130.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = Typography.labelMedium,
                fontSize = 12.sp,
                color = textColor
            )
        }
    }
}

