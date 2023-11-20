package com.pat.presentation.ui.common

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.material.timepicker.TimeFormat
import com.ozcanalasalvar.datepicker.compose.timepicker.WheelTimePicker
import com.ozcanalasalvar.datepicker.model.Time

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheelTimePickerView(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    sheetState: SheetState,
    timeState: MutableState<String>
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        var tempTime by remember { mutableStateOf("") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier
                    .weight(1f)
                    .clickable {
                        timeState.value =
                            convertTimeText(tempTime)
                        onDismiss()

                    }) {
                Text("확인")
            }
            Box(
                modifier
                    .weight(1f)
                    .clickable {
                        onDismiss()
                    }) {
                Text("취소")
            }
        }
        WheelTimePicker(
            timeFormat = TimeFormat.CLOCK_12H,
            startTime = Time(hour = 24, minute = 0, format = "AM"),
            selectorEffectEnabled = false,
            onTimeChanged = { hour, minute, format ->
                tempTime = "$format:$hour:$minute"
            }
        )
    }
}
