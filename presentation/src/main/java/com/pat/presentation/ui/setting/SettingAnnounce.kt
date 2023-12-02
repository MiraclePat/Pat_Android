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
                title = "공지 제목",
                date = "2023.12.12",
                body = "공지 내용이 들어갈 예정입니다. 공지 내용이 들어갈 예정입니다. 공지 내용이 들어갈 예정입니다. 공지 내용이 들어갈 예정입니다."
            )
            SettingAnnounceBox(
                title = "공지 제목",
                date = "2023.12.12",
                body = "공지 내용이 들어갈 예정입니다. 공지 내용이 들어갈 예정입니다. 공지 내용이 들어갈 예정입니다. 공지 내용이 들어갈 예정입니다."
            )
        }
    }
}