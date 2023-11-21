package com.pat.presentation.ui.post

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBoxList
import com.pat.presentation.ui.common.CheckBoxView
import com.pat.presentation.ui.common.CustomDialog
import com.pat.presentation.ui.common.CustomPicker
import com.pat.presentation.ui.common.CustomTextField
import com.pat.presentation.ui.common.DateTimePickerView
import com.pat.presentation.ui.common.ExampleImageView
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.SelectImageList
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenView(onNavigateToHome: () -> Unit) {
    val scrollState = rememberScrollState()
    val declarationDialogState = remember { mutableStateOf(false) }
    if (declarationDialogState.value) {
        CustomDialog(okRequest = onNavigateToHome, state = declarationDialogState)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "공고글 작성",
                        fontSize = 14.sp,
                        style = Typography.labelMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        declarationDialogState.value = !declarationDialogState.value
                        Log.e("custom", "${declarationDialogState.value}")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PostScreenBody(onNavigateToHome = onNavigateToHome)
        }
    }
}

@Composable
fun PostScreenBody(modifier: Modifier = Modifier, onNavigateToHome: () -> Unit) {
    val isRealTime = remember { mutableStateOf(false) }         // 사진 선택
    val isGallery = remember { mutableStateOf(false) }          // 갤러리 선택

    val title = rememberSaveable { mutableStateOf("") }         // 팟 제목
    val maxPerson = rememberSaveable { mutableStateOf("") }     // 최대 인원
    val patDetail = rememberSaveable { mutableStateOf("") }     // 팟 소개
    val proofDetail = rememberSaveable { mutableStateOf("") }   // 인증 방법 설명
    val startDate = remember { mutableStateOf("") }             // 시작 날짜
    val endDate = remember { mutableStateOf("") }               // 종료 날짜
    val startTime = remember { mutableStateOf("") }             // 시작 시간
    val endTime = remember { mutableStateOf("") }               // 종료 시간
    val day = remember { mutableStateOf("") }                   // 인증 빈도
    val category = remember { mutableStateOf("") }              // 카테고리


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
                    .border(1.dp, color = PrimaryMain)
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
            Row {
//                CategoryButtonList()
                CategoryBoxList(state = category)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 제목", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "최대 15자", state = title, maxLength = 15)
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
            CustomTextField(
                placeholderText = "숫자만 입력해주세요. (최대 10,000명 가능)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                state = maxPerson,
                maxLength = 5
            )
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
                    content = {
                    },
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
            CustomTextField(placeholderText = "최대 500자", state = patDetail, maxLength = 500)
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 빈도", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            Row() {
                SelectDayButtonList(state = day)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증방법 설명", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "최대 30자", state = proofDetail, maxLength = 30)
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

            FinalButton(text = "확정",
                backColor = PrimaryMain,
                textColor = White,
                onClick = {
                Log.e("custom", "main startDate : $startDate")
                Log.e("custom", "main title : ${title.value}")
                Log.e("custom", "main maxPeople : ${maxPerson.value}")
                Log.e("custom", "main maxPeople : ${day.value}")
                Log.e("custom", "main maxPeople : ${category.value}")
                onNavigateToHome()
            })
        }
    }
}


@Composable
fun DayButtonView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    isSelected: Boolean = false
) {
    val buttonColor = if (isSelected) Primary50 else White
    val textColor = if (isSelected) PrimaryMain else Gray500
    val border = if (isSelected) PrimaryMain else Gray500

    Button(
        modifier = modifier
            .requiredHeight(32.dp),
        shape = RoundedCornerShape(22.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, border),
        onClick = {
            onClick()
        },
    ) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun SelectDayButtonList(state: MutableState<String>) {
    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

    @Composable
    fun dayButtonView(day: String) {
        DayButtonView(
            text = day,
            onClick = {
                state.value = day
            },
            isSelected = state.value == day
        )
        Spacer(Modifier.size(10.dp))
    }

    Column {
        Row() {
            days.take(5).forEach { day ->
                dayButtonView(day)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            days.takeLast(2).forEach { day ->
                dayButtonView(day)
            }
        }
    }
}