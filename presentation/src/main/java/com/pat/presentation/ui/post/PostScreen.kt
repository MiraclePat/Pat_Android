package com.pat.presentation.ui.post

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
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
import com.pat.presentation.ui.common.WheelTimePickerView
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.post.components.PostRepImageView
import com.pat.presentation.ui.post.components.SelectDayButtonList
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenView(
    navController: NavController,
    onNavigateToHome: () -> Unit,
    viewModel: PostViewModel
) {

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

            PostScreenBody(
                navController = navController,
                onNavigateToHome = onNavigateToHome,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenBody(
    navController: NavController,
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: PostViewModel
) {
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

    val bodyBitmap by viewModel.bodyBitmap.collectAsState() //팟 상세이미지들

    val correctBitmap by viewModel.correctBitmap.collectAsState() //올바른 이미지
    val incorrectBitmap by viewModel.incorrectBitmap.collectAsState() //나쁜이미지!
    val repBitmap by viewModel.repBitmap.collectAsState() //썸네일


    Column {
        PostRepImageView(navController = navController, bitmap = repBitmap, viewModel = viewModel)

        Spacer(modifier = modifier.size(20.dp))
        Column(modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Text(text = "카테고리 선택", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            Row {
                CategoryBoxList(state = category)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 제목", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(placeholderText = "최대 15자", state = title, maxLength = 15)
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "팟 상세정보", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            SelectImageList(navController = navController, bitmapList = bodyBitmap, bitmapType = "BODY", viewModel = viewModel)
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
                val sheetState = rememberModalBottomSheetState()
                CustomPicker(
                    text = "시작시간",
                    dateState = startTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            startPressed.value = !startPressed.value
                        }, sheetState = sheetState, timeState = startTime)
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
                ExampleImageView(
                    navController = navController,
                    text = "올바른 예시",
                    backColor = GreenBack,
                    textColor = GreenText,
                    bitmap = correctBitmap,
                    bitmapType = "CORRECT",
                    viewModel = viewModel
                )
                Spacer(modifier = modifier.size(10.dp))
                ExampleImageView(
                    navController = navController,
                    text = "잘못된 예시",
                    backColor = RedBack,
                    textColor = RedText,
                    bitmap = incorrectBitmap,
                    bitmapType = "INCORRECT",
                    viewModel = viewModel
                )
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 수단", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))

            Row {
                CheckBoxView(checked = isRealTime, text = "사진으로 인증")
                Spacer(modifier = modifier.size(12.dp))
                CheckBoxView(checked = isGallery, text = "실시간 촬영과 사진 가능")
            }
            Spacer(modifier = modifier.size(55.dp))

            FinalButton(text = "확정",
                backColor = PrimaryMain,
                textColor = White,
                onClick = {
                    viewModel.post(
                        patName = title.value,
                        maxPerson = maxPerson.value.toInt(),
                        patDetail = patDetail.value,
                        proofDetail = proofDetail.value,
                        startDate = startDate.value,
                        endDate = endDate.value,
                        startTime = startTime.value,
                        endTime = endTime.value,
                        days = "", //다중선택 pull받아야함
                        category = category.value,
                        latitude = 0.0,
                        longitude = 0.0,
                        location = "서울",
                        realtime = isGallery.value,

                    )
                })
        }
    }
}


