package com.pat.presentation.ui.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pat.presentation.R
import com.pat.presentation.ui.setting.components.SettingBox
import com.pat.presentation.ui.setting.components.SettingTitle
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.pat.presentation.util.ALARM
import com.pat.presentation.util.BODY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingAlarm(modifier: Modifier = Modifier, viewState: MutableState<String>) {
    BackHandler { viewState.value = BODY }
    @Composable
    fun switch(checked: MutableState<Boolean>) {
        Switch(
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = PrimaryMain,
                uncheckedBorderColor = White,
                uncheckedThumbColor = White,
                uncheckedTrackColor = Gray300,
            )
        )
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = ALARM, style = Typography.labelMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { viewState.value = BODY }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SettingTitle(text = "푸쉬메시지 수신")
            SettingBox(text = "팟 인증날 알림", setEnd = true) {
                val checked = remember { mutableStateOf(false) }
                switch(checked)
            }
        }
    }
}