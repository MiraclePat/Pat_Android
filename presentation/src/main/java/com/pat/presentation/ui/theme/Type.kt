package com.pat.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pat.presentation.R

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_black)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Gray900
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_black)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = Gray900
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)