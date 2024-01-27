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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pat.presentation.R
import com.pat.presentation.ui.setting.components.SettingAnnounceBox
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.util.ANNOUNCE
import com.pat.presentation.util.BODY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingAnnounce(modifier: Modifier = Modifier, viewState: MutableState<String>) {
    BackHandler { viewState.value = BODY }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = ANNOUNCE, style = Typography.labelMedium)
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
            SettingAnnounceBox(
                title = "공지사항",
                date = "2024.01.25",
                body = "미라클 팟에 가입해주셔서 감사합니다. 모두 정하신 목표 이루셨으면 좋겠습니다."
            )
        }
    }
}