package com.pat.presentation.ui.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.pat.presentation.R
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.setting.components.SettingBox
import com.pat.presentation.ui.setting.components.SettingTextField
import com.pat.presentation.ui.setting.components.SettingTitle
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.util.ACCOUNT
import com.pat.presentation.util.PASSWORD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPassword(modifier: Modifier = Modifier, viewState: MutableState<String>) {
    val nowPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val newPasswordCheck = remember { mutableStateOf("") }
    BackHandler { viewState.value = ACCOUNT }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = PASSWORD, style = Typography.labelMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { viewState.value = ACCOUNT }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_arrow),
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
            SettingTitle(text = "현재 비밀번호", color = Gray600)
            SettingTextField(placeholderText = "현재 비밀번호를 입력해주세요.?", state = nowPassword)
            SettingTitle(text = "새로운 비밀번호", color = Gray600)
            SettingTextField(state = newPassword)
            SettingTitle(text = "새로운 비밀번호", color = Gray600)
            SettingTextField(state = newPasswordCheck)
            Spacer(modifier = modifier.weight(1f))
            FinalButton(
                modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 26.dp),
                text = "비밀번호 변경하기",
                backColor = Gray200,
                textColor = Gray600,
                cornerShape = RoundedCornerShape(100.dp),
                stokeColor = Gray200,
                onClick = {
                    // TODO 비밀번호 변경하기
                }
            )
        }
    }
}