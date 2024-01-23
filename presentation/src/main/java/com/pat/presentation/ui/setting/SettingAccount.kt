package com.pat.presentation.ui.setting

import androidx.activity.compose.BackHandler
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pat.presentation.R
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.setting.components.SettingBox
import com.pat.presentation.ui.setting.components.SettingTitle
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.util.ACCOUNT
import com.pat.presentation.util.BODY
import com.pat.presentation.util.LOGOUT
import com.pat.presentation.util.PASSWORD
import com.pat.presentation.util.WITHDRAWAL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingAccount(
    modifier: Modifier = Modifier,
    viewState: MutableState<String>,
    navController: NavController,
    viewModel: SettingViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    BackHandler { viewState.value = BODY }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = ACCOUNT, style = Typography.labelMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { viewState.value = BODY }) {
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
            SettingTitle(text = "로그인")
            SettingBox(text = LOGOUT, onClick = { showBottomSheet = true })
            SettingBox(text = WITHDRAWAL, onClick = { viewState.value = WITHDRAWAL })
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "정말 로그아웃 하시겠습니까?",
                    style = Typography.titleLarge,
                    color = Gray800
                )
                Spacer(modifier = modifier.padding(16.dp))
                SelectButton(
                    text = "로그아웃 하기",
                    onClick = {
                       viewModel.logout()
                    },
                    cornerSize = 100.dp,
                )
                Spacer(modifier = modifier.padding(10.dp))
                SelectButton(
                    text = "취소하기",
                    onClick = { showBottomSheet = false },
                    cornerSize = 100.dp,
                    backColor = Gray200,
                    textColor = Gray600,
                    stokeColor = Gray200
                )
                Spacer(modifier = modifier.padding(30.dp))
            }
        }
    }
}