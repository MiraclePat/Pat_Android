package com.pat.presentation.ui.post.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.White

@Composable
fun DayButtonView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    isSelected: Boolean = false
) {
    val buttonColor = if (isSelected) Primary50 else White
    val textColor = if (isSelected) PrimaryMain else Gray500
    val border = if (isSelected) PrimaryMain else Gray500

    Button(
        modifier = modifier
            .requiredHeight(32.dp),
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

//@Composable
//fun SelectDayButtonList(state: MutableState<String>) {
//    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
//
//    @Composable
//    fun dayButtonView(day: String) {
//        DayButtonView(
//            text = day,
//            onClick = {
//                state.value = day
//            },
//            isSelected = state.value == day
//        )
//        Spacer(Modifier.size(10.dp))
//    }
//
//    Column {
//        Row() {
//            days.take(5).forEach { day ->
//                dayButtonView(day)
//            }
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Row() {
//            days.takeLast(2).forEach { day ->
//                dayButtonView(day)
//            }
//        }
//    }
//}