package com.pat.presentation.ui.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray50
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.SpanStyleType
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.util.ACCOUNT
import com.pat.presentation.util.BODY
import com.pat.presentation.util.WITHDRAWAL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingWithdrawal(modifier: Modifier = Modifier, viewState: MutableState<String>) {
    BackHandler { viewState.value = ACCOUNT }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = WITHDRAWAL, style = Typography.labelMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { viewState.value = ACCOUNT }) {
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.size(130.dp))
            Text("잠깐! 탈퇴하기 전에", style = Typography.displayLarge, fontSize = 20.sp, color = Gray800)
            Text("한번 더 생각해주세요.", style = Typography.displayLarge, fontSize = 20.sp, color = Gray800)
            Spacer(modifier = modifier.size(12.dp))
            Column(
                modifier
                    .padding(top = 12.dp, start = 28.dp, end = 28.dp)
                    .fillMaxWidth()
                    .height(62.dp)
                    .border(1.dp, Gray100, RoundedCornerShape(16.dp))
                    .background(Gray50),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("미라클팟을 탈퇴하시게 되면", style = Typography.labelMedium, color = Gray600)
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyleType.copy(
                                fontSize = 14.sp,
                                color = PrimaryMain,
                            )
                        ) {
                            append("그동안 완료해온 기적들")
                        }
                        withStyle(
                            style = SpanStyleType.copy(
                                fontSize = 14.sp,
                                color = Gray800,
                            )
                        ) {
                            append("이 모두 사라지게돼요.")
                        }
                    }
                )

            }
            Spacer(modifier = modifier.size(32.dp))
            Text(
                "그래도 정말 탈퇴하시겠어요?",
                style = Typography.labelMedium,
                color = Gray600,
                fontSize = 16.sp
            )
            Spacer(modifier = modifier.weight(1f))
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 26.dp)
            ) {
                SelectButton(
                    modifier = modifier.weight(1f),
                    text = "취소하기",
                    onClick = { viewState.value = BODY },
                    cornerSize = 100.dp,
                    backColor = Gray200,
                    textColor = Gray600,
                    stokeColor = Gray200
                )
                Spacer(modifier = modifier.padding(10.dp))
                SelectButton(
                    modifier = modifier.weight(1f),
                    text = "탈퇴하기",
                    onClick = {
                        // TODO 탈퇴하기
                    },
                    cornerSize = 100.dp,
                )
            }
        }
    }
}