package com.pat.presentation.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


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

const val BODY = "마이페이지"
const val ACCOUNT = "계정 설정"
const val ALARM = "알림 정보 설정"
const val ANNOUNCE = "공지사항"
const val PASSWORD = "비밀번호 수정"
const val LOGOUT = "로그아웃"
const val WITHDRAWAL = "회원탈퇴"
