package com.pat.presentation.ui.post

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.common.CheckBoxView
import com.pat.presentation.ui.common.CustomPicker
import com.pat.presentation.ui.common.CustomTextField
import com.pat.presentation.ui.common.DateTimePickerView
import com.pat.presentation.ui.common.ExampleImageView
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.SelectImageList
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenView(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "공고글 수정",
                        fontSize = 14.sp,
                        style = Typography.labelMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Delete"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostScreenBody()
        }
    }
}

@Composable
fun PostScreenBody(modifier: Modifier = Modifier) {
    val isRealTime = remember { mutableStateOf(false) }
    val isGallery = remember { mutableStateOf(false) }

    val title = remember { mutableStateOf("") }
    val maxPerson = remember { mutableIntStateOf(0) }
    val patDetail = remember { mutableStateOf("") } // 팟 소개
    val proofDetail = remember { mutableStateOf("") } // 인증 방법 설명
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val startTime = remember { mutableStateOf("") }
    val endTime = remember { mutableStateOf("") }

    Column() {
        Box(
            modifier
                .background(Gray100)
                .fillMaxWidth()
                .height(160.dp)
                .clickable {
                    // TODO click Event
                },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = modifier
                    .width(141.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(2.dp, color = PrimaryMain)
                    .background(White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "썸네일 추가하기",
                    style = Typography.labelMedium,
                    color = PrimaryMain,
                )
                Spacer(modifier = modifier.size(4.dp))
                Icon(
                    modifier = modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "썸네일 추가"
                )
            }
        }

        Spacer(modifier = modifier.size(20.dp))
        Column(modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Text(text = "카테고리 선택", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            // TODO 카테고리 버튼
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 제목", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "최대 15자")
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 상세정보", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            SelectImageList()
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "위치정보 유무", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            // TODO 위치 정보 유무
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 인원 설정", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "숫자만 입력해주세요. (최대 10,000명 가능)")
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "시작일-종료일", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작일 선택",
                    dateState = startDate,
                    formatter = convertDateFormat,
                    widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { startDate.value = it },
                            onDismiss = { startPressed.value = !startPressed.value },
                        )
                    },
                    clickState = startPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("부터 시작", style = Typography.bodySmall)
                Spacer(modifier = modifier.padding(10.dp))
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료일 선택",
                    dateState = endDate,
                    formatter = convertDateFormat,
                    widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { endDate.value = it },
                            onDismiss = { endPressed.value = !endPressed.value },
                        )
                    },
                    clickState = endPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("에 종료", style = Typography.bodySmall)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 가능 시간", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작시간",
                    dateState = startTime,
                    formatter = convertTimeFormat,
                    content = {},
                    clickState = startPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("부터", style = Typography.bodySmall)
                Spacer(modifier = modifier.padding(10.dp))
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료시간",
                    dateState = endTime,
                    formatter = convertTimeFormat,
                    content = {},
                    clickState = endPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("까지", style = Typography.bodySmall)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 소개", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "최대 500자")
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 빈도", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            // TODO 월~토 chip
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증방법 설명", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "최대 30자")
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증사진 예시", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            Row() {
                ExampleImageView(text = "올바른 예시", backColor = GreenBack, textColor = GreenText)
                Spacer(modifier = modifier.size(10.dp))
                ExampleImageView(text = "잘못된 예시", backColor = RedBack, textColor = RedText)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 수단", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))

            Row {
                CheckBoxView(checked = isRealTime, text = "실시간 촬영")
                Spacer(modifier = modifier.size(12.dp))
                CheckBoxView(checked = isGallery, text = "갤러리에서 사진 가져오기")
            }
            Spacer(modifier = modifier.size(55.dp))

            FinalButton(text = "확정", onClick = { Log.e("custom", "main startDate : $startDate") })
        }
    }
}

