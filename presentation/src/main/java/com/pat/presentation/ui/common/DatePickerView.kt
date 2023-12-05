package com.pat.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orhanobut.logger.Logger
import com.pat.presentation.ui.theme.StarColor
import com.pat.presentation.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerView(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val currentMillis = System.currentTimeMillis()
            val oneDayInMillis = 24 * 60 * 60 * 1000
            val result = currentMillis + oneDayInMillis

            return utcTimeMillis >= result
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""


    DatePickerDialog(
        shape = RoundedCornerShape(6.dp),
        onDismissRequest = onDismiss,
        confirmButton = {},
    ) {
        DatePicker(modifier = Modifier.weight(1f), state = datePickerState)
        Row(
            Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("* 2일 뒤 부터 고를 수 있어요.", style = Typography.labelMedium, color = StarColor)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = {
                onDismiss()
                onDateSelected("")
            }) {
                Text(text = "취소", style = Typography.labelMedium)
            }
            TextButton(onClick = {
                onDismiss()
                onDateSelected(selectedDate)
            }) {
                Text(text = "확인", style = Typography.labelMedium)
            }
        }
    }
}
