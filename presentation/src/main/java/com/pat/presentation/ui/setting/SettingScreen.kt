package com.pat.presentation.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.pat.presentation.ui.pat.PatUpdateViewModel
import com.pat.presentation.util.ACCOUNT
import com.pat.presentation.util.ALARM
import com.pat.presentation.util.ANNOUNCE
import com.pat.presentation.util.BODY
import com.pat.presentation.util.PASSWORD
import com.pat.presentation.util.WITHDRAWAL


@Composable
fun SettingScreenView(
    settingViewModel: SettingViewModel= hiltViewModel(),
) {
    val viewState = remember { mutableStateOf(BODY) }
    val uiState by settingViewModel.uiState.collectAsState()

    when (viewState.value) {
        BODY -> SettingScreenBody(viewState = viewState, content = uiState.profileContent)
        ACCOUNT -> SettingAccount(viewState = viewState)
        ALARM -> SettingAlarm(viewState = viewState)
        ANNOUNCE -> SettingAnnounce(viewState = viewState)
        PASSWORD -> SettingPassword(viewState = viewState)
        WITHDRAWAL -> SettingWithdrawal(viewState = viewState)
    }
}

