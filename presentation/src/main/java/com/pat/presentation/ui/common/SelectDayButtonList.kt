package com.pat.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray500

@Composable
fun SelectDayButtonList(state: SnapshotStateList<String>) {
    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

    @Composable
    fun dayButtonView(day: String) {
        CustomButtonView(
            modifier = Modifier.requiredHeight(32.dp),
            text = day,
            onClick = {
                if (state.contains(day)) {
                    state.remove(day)
                } else {
                    state.add(day)
                }
            },
            isSelected = state.contains(day),
            shape = RoundedCornerShape(22.dp),
            fontSize = 13.sp,
            borderColor = Gray300,
            textColor = Gray500
        )
        Spacer(Modifier.size(10.dp))
    }

    Column {
        Row() {
            days.take(5).forEach { day ->
                dayButtonView(day)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            days.takeLast(2).forEach { day ->
                dayButtonView(day)
            }
        }
    }
}