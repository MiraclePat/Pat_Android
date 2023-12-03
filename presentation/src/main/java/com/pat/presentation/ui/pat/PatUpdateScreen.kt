package com.pat.presentation.ui.pat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.PatDetailContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBoxList
import com.pat.presentation.ui.common.CheckBoxView
import com.pat.presentation.ui.common.CustomDialog
import com.pat.presentation.ui.common.CustomPicker
import com.pat.presentation.ui.common.CustomTextField
import com.pat.presentation.ui.common.DateTimePickerView
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.SelectDayButtonList
import com.pat.presentation.ui.common.WheelTimePickerView
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.common.convertTimeViewFormat
import com.pat.presentation.ui.navigations.HOME
import com.pat.presentation.ui.pat.components.UpdateExampleImageView
import com.pat.presentation.ui.pat.components.UpdateRepImageView
import com.pat.presentation.ui.pat.components.UpdateSelectImageList
import com.pat.presentation.ui.pat.components.UpdateSelectLocationButtonList
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatUpdateView(
    modifier: Modifier = Modifier,
    patId: Long = -1,
    patUpdateViewModel: PatUpdateViewModel,
    navController: NavController,
) {
    //patid 가 -1이 아닐경우에 호출
    //카메라 뷰에서 이동
    patUpdateViewModel.getPatDetail(patId)
    val uiState by patUpdateViewModel.uiState.collectAsState()
    val deleteDialogState = remember { mutableStateOf(false) }
    val updateDialogState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    BackHandler {
        updateDialogState.value = true
    }
    if (updateDialogState.value) {
        CustomDialog(
            okRequest = { navController.popBackStack() }, state = updateDialogState,
            message = "공고글 수정을 취소하시겠어요?",
            cancelMessage = "계속 작성",
            okMessage = "작성 취소"
        )
    }
    if (deleteDialogState.value) {
        CustomDialog(
            okRequest = {
                if (uiState.content != null) patUpdateViewModel.deletePat(uiState.content!!.patId)
                navController.popBackStack(
                    route = HOME,
                    inclusive = false
                )
            },
            state = deleteDialogState,
            message = "이 공고글을 삭제하시겠어요?",
            cancelMessage = "공고글 유지",
            okMessage = "삭제하기"
        )
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "공고글 수정",
                        fontSize = 14.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        deleteDialogState.value = true
                    }) {
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
        ) {
            if (uiState.content != null) {
                PatUpdateScreen(
                    content = uiState.content!!,
                    viewModel = patUpdateViewModel,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun PatUpdateScreen(
    modifier: Modifier = Modifier,
    content: PatDetailContent,
    viewModel: PatUpdateViewModel,
    navController: NavController,
) {
    val isRealTime = remember { mutableStateOf(false) }         // 사진 선택
    val isGallery = remember { mutableStateOf(false) }          // 갤러리 선택

    val title = rememberSaveable { mutableStateOf(content.patName) }         // 팟 제목
    val maxPerson = rememberSaveable { mutableStateOf(content.maxPerson.toString()) }     // 최대 인원
    val patDetail = rememberSaveable { mutableStateOf(content.patDetail) }     // 팟 소개
    val proofDetail = rememberSaveable { mutableStateOf(content.proofDetail) }   // 인증 방법 설명
    val startDate = remember { mutableStateOf(content.startDate) }             // 시작 날짜
    val endDate = remember { mutableStateOf(content.endDate) }               // 종료 날짜
    val startTime =
        remember { mutableStateOf(convertTimeViewFormat(content.startTime)) }             // 시작 시간
    val endTime =
        remember { mutableStateOf(convertTimeViewFormat(content.endTime)) }               // 종료 시간
    val category = remember { mutableStateOf(content.category) }              // 카테고리
    val dayList = remember { mutableStateListOf<String>() }                   // 인증 빈도
    content.dayList.forEach {
        dayList.add(it)
    }
    val locationSelect = remember { mutableStateOf("") }        // 주소 입력 방식
    val locationSearchValue = remember { mutableStateOf(content.location) }        // 주소 입력 방식
    val onSearchScreen = remember { mutableStateOf(false) }

    val bodyBitmap by viewModel.bodyBitmap.collectAsState() //팟 상세이미지들
    val correctBitmap by viewModel.correctBitmap.collectAsState() //올바른 이미지
    val incorrectBitmap by viewModel.incorrectBitmap.collectAsState() //나쁜이미지!
    val repBitmap by viewModel.repBitmap.collectAsState() //썸네일

    val searchPlaceResult by viewModel.searchPlaceResult.collectAsState() //주소검색결과

    LaunchedEffect(content) {
        Logger.t("MainTest").i("수정 ${content}")
    }

    Column() {
        UpdateRepImageView(
            navController = navController,
            bitmap = repBitmap,
            viewModel = viewModel
        )

    }

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
        CustomTextField(placeholderText = "최대 15자", maxLength = 15, state = title)
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "팟 상세정보", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(14.dp))
        UpdateSelectImageList(
            navController = navController,
            bitmapList = bodyBitmap,
            bitmapType = "BODY",
            viewModel = viewModel
        )
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "위치정보", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(16.dp))
        Row(
            modifier
                .fillMaxWidth()
                .height(36.dp)
        ) {
            UpdateSelectLocationButtonList(
                locationState = locationSelect,
                viewModel = viewModel,
                searchValue = locationSearchValue,
                onSearchScreen = onSearchScreen,
                searchPlaceResult = searchPlaceResult,
            )        }
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "팟 인원 설정", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(14.dp))
        CustomTextField(
            placeholderText = "숫자만 입력해주세요. (최대 10,000명 가능)",
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
                content = {
                    WheelTimePickerView(onDismiss = {
                        startPressed.value = !startPressed.value
                    }, timeState = startTime)
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
                content = {
                    WheelTimePickerView(onDismiss = {
                        endPressed.value = !endPressed.value
                    }, timeState = endTime)
                },
                clickState = endPressed
            )
            Spacer(modifier = modifier.padding(8.dp))
            Text("까지", style = Typography.bodySmall)
        }
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "팟 소개", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(14.dp))
        CustomTextField(placeholderText = "최대 500자", maxLength = 500, state = patDetail)
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "인증 빈도", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(14.dp))
        Row() {
            SelectDayButtonList(state = dayList)
        }
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "인증방법 설명", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(14.dp))
        CustomTextField(placeholderText = "최대 30자", maxLength = 30, state = proofDetail)
        Spacer(modifier = modifier.size(36.dp))

        Text(text = "인증사진 예시", style = Typography.titleLarge)
        Spacer(modifier = modifier.size(14.dp))
        Row() {
            UpdateExampleImageView(
                navController = navController,
                text = "올바른 예시",
                backColor = GreenBack,
                textColor = GreenText,
                bitmap = correctBitmap,
                bitmapType = "CORRECT",
                viewModel = viewModel
            )
            Spacer(modifier = modifier.size(10.dp))
            UpdateExampleImageView(
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
            CheckBoxView(checked = isRealTime, text = "실시간 촬영")
            Spacer(modifier = modifier.size(12.dp))
            CheckBoxView(checked = isGallery, text = "갤러리에서 사진 가져오기")
        }
        Spacer(modifier = modifier.size(55.dp))

        FinalButton(text = "확정",
            backColor = PrimaryMain,
            textColor = White,
            onClick = {
                val outputStartTime = convertTimeFormat(startTime.value)
                val outputEndTime = convertTimeFormat(endTime.value)
                viewModel.updatePat(
                    patName = title.value,
                    maxPerson = maxPerson.value.toInt(),
                    patDetail = patDetail.value,
                    proofDetail = proofDetail.value,
                    startDate = startDate.value,
                    endDate = endDate.value,
                    startTime = outputStartTime,
                    endTime = outputEndTime,
                    days = dayList.toList(),
                    category = category.value,
                    realtime = !isRealTime.value,
                )
                navController.popBackStack(
                    route = HOME,
                    inclusive = false
                )
            })
    }
}
