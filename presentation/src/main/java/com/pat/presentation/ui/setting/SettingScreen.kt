package com.pat.presentation.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pat.presentation.util.ACCOUNT
import com.pat.presentation.util.ALARM
import com.pat.presentation.util.ANNOUNCE
import com.pat.presentation.util.BODY
import com.pat.presentation.util.PASSWORD
import com.pat.presentation.util.WITHDRAWAL


@Composable
fun SettingScreenView() {
    val viewState = remember { mutableStateOf(BODY) }

    when (viewState.value) {
        BODY -> SettingScreenBody(viewState = viewState)
        ACCOUNT -> SettingAccount(viewState = viewState)
        ALARM -> SettingAlarm(viewState = viewState)
        ANNOUNCE -> SettingAnnounce(viewState = viewState)
        PASSWORD -> SettingPassword(viewState = viewState)
        WITHDRAWAL -> SettingWithdrawal(viewState = viewState)
    }
}

