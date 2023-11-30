package com.pat.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.White


@Composable
fun DayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    buttonColor: Color = Primary50,
    textColor: Color = PrimaryMain,
    border: Color = PrimaryMain
) {

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

@Composable
fun DayButtonList(
    dayList: List<String>
) {
    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
    Column {
        Row(
        ) {
            days.take(5).forEach { day ->
                if (day in dayList) {
                    DayButton(text = day)
                } else {
                    DayButton(
                        text = day,
                        buttonColor = White,
                        textColor = Gray500,
                        border = Gray500
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
        ) {
            days.takeLast(2).forEach { day ->
                if (day in dayList) {
                    DayButton(text = day)

                } else {
                    DayButton(
                        text = day,
                        buttonColor = White,
                        textColor = Gray500,
                        border = Gray500
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
    Spacer(Modifier.size(10.dp))
}
