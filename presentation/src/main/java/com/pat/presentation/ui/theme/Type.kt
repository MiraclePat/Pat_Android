package com.pat.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.pat.presentation.R

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = PrimaryMain,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = Gray700,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Gray900,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = Gray900,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Gray700,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Gray700,
    )
)