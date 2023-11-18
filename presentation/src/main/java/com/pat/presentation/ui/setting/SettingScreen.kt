package com.pat.presentation.ui.setting

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.pat.presentation.ui.common.DatePickerView

@Composable
fun SettingScreenView() {
    val scrollState = rememberScrollState()

    Surface {
        Text(text = "Hello, Setting")
    }
}

