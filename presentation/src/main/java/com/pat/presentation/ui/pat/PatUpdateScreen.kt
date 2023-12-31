package com.pat.presentation.ui.pat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
import com.pat.presentation.ui.common.SnackBar
import com.pat.presentation.ui.common.WheelTimePickerView
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.common.convertTimeViewFormat
import com.pat.presentation.ui.pat.components.UpdateExampleImageView
import com.pat.presentation.ui.pat.components.UpdateRepImageView
import com.pat.presentation.ui.pat.components.UpdateSelectImageList
import com.pat.presentation.ui.pat.components.UpdateSelectLocationButtonList
import com.pat.presentation.ui.post.MAX_DETAIL
import com.pat.presentation.ui.post.MAX_PERSON
import com.pat.presentation.ui.post.MAX_PROOF
import com.pat.presentation.ui.post.MAX_TITLE
import com.pat.presentation.ui.post.MIN_DETAIL
import com.pat.presentation.ui.post.MIN_PERSON
import com.pat.presentation.ui.post.MIN_PROOF
import com.pat.presentation.ui.post.MIN_TITLE
import com.pat.presentation.ui.post.compareDates
import com.pat.presentation.ui.post.compareTimes
import com.pat.presentation.ui.post.components.PostTitleBox
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.pat.presentation.util.HOME


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatUpdateView(
    modifier: Modifier = Modifier,
    patId: Long,
    patUpdateViewModel: PatUpdateViewModel,
    navController: NavController,
) {

    if (patId != (-1).toLong()) {
        patUpdateViewModel.getPatDetail(patId)
    }
    val uiState by patUpdateViewModel.uiState.collectAsState()
    val deleteDialogState = remember { mutableStateOf(false) }
    val updateDialogState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val errorMessage = remember { mutableStateOf("") }

    BackHandler {
        updateDialogState.value = true
    }

    LaunchedEffect(Unit) {
        patUpdateViewModel.event.collect {
            when (it) {
                is UpdateEvent.UpdateSuccess -> {
                    patUpdateViewModel.clearImageData()
                    navController.popBackStack()
                }

                is UpdateEvent.UpdateFailed -> {
                    errorMessage.value = "업데이트 실패"
                }

                is UpdateEvent.DeleteSuccess -> {
                    patUpdateViewModel.clearImageData()
                    navController.popBackStack()
                }

                is UpdateEvent.DeleteFailed -> {
                    errorMessage.value = "삭제 실패"
                }

                is UpdateEvent.SearchCoordinateFailed -> {}
                is UpdateEvent.SearchCoordinateSuccess -> {}
                is UpdateEvent.SearchPlaceFailed -> {}
                is UpdateEvent.SearchPlaceSuccess -> {}
            }
        }
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
                patUpdateViewModel.deletePat(uiState.content?.patId ?: -1)
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
                    navController = navController,
                    scrollState = scrollState
                )
            }
        }

        if (errorMessage.value.isNotEmpty()) {
            SnackBar(errorMessage)
        }
    }
}


@Composable
fun PatUpdateScreen(
    modifier: Modifier = Modifier,
    content: PatDetailContent,
    viewModel: PatUpdateViewModel,
    navController: NavController,
    scrollState: ScrollState
) {
    var scrollToPosition by remember { mutableFloatStateOf(0F) }

    val isRealTime = rememberSaveable { mutableStateOf(false) }         // 사진 선택
    val title = rememberSaveable { mutableStateOf(content.patName) }         // 팟 제목
    val maxPerson = rememberSaveable { mutableStateOf(content.maxPerson.toString()) }     // 최대 인원
    val patDetail = rememberSaveable { mutableStateOf(content.patDetail) }     // 팟 소개
    val proofDetail = rememberSaveable { mutableStateOf(content.proofDetail) }   // 인증 방법 설명
    val startDate =
        rememberSaveable { mutableStateOf(content.startDate) }             // 시작 날짜
    val endDate =
        rememberSaveable { mutableStateOf(content.endDate) }               // 종료 날짜
    val startTime =
        rememberSaveable { mutableStateOf(convertTimeViewFormat(content.startTime)) }             // 시작 시간
    val endTime =
        rememberSaveable { mutableStateOf(convertTimeViewFormat(content.endTime)) }               // 종료 시간
    val category = rememberSaveable { mutableStateOf(content.category) }              // 카테고리
    val dayList = rememberSaveable { mutableStateOf(content.dayList) }                   // 인증 빈도

    val originalState = if (content.location != "") "주소 검색" else "위치정보 없음"
    val originalScreenState = content.location != ""

    val locationSelect = rememberSaveable { mutableStateOf(originalState) }        // 주소 입력 방식
    val locationSearchValue =
        rememberSaveable { mutableStateOf(content.location) }        // 주소 입력 방식
    val placeSelected = rememberSaveable { mutableStateOf(content.location != "") }
    val onSearchScreen = rememberSaveable { mutableStateOf(originalScreenState) }

    val bodyBitmap by viewModel.bodyBitmap.collectAsState() //팟 상세이미지들
    val correctBitmap by viewModel.correctBitmap.collectAsState() //올바른 이미지
    val incorrectBitmap by viewModel.incorrectBitmap.collectAsState() //나쁜이미지!
    val repBitmap by viewModel.repBitmap.collectAsState() //썸네일
    val searchPlaceResult by viewModel.searchPlaceResult.collectAsState() //주소검색결과

    val checkRepImage = repBitmap != null
    val checkCategory = category.value.isNotEmpty()
    val checkTitle = title.value.length in (MIN_TITLE..MAX_TITLE)
    val checkLocation =
        (locationSelect.value == "주소 검색" && locationSearchValue.value.isNotEmpty() && placeSelected.value)
                || locationSelect.value == "위치정보 없음"
    val checkPerson =
        maxPerson.value.isNotEmpty() && maxPerson.value.toInt() in MIN_PERSON..MAX_PERSON
    val checkDate = compareDates(startDate.value, endDate.value)
    val checkTime = compareTimes( // 시작 시간 < 종료 시간 -> true
        convertTimeFormat(startTime.value), convertTimeFormat(endTime.value)
    )
    val checkDetail = patDetail.value.length in (MIN_DETAIL..MAX_DETAIL)
    val checkDay = dayList.value.isNotEmpty()
    val checkProof = proofDetail.value.length in (MIN_PROOF..MAX_PROOF)
    val checkCorrect = correctBitmap != null && incorrectBitmap != null
    val checkList = listOf(
        checkRepImage, checkCategory, checkTime, checkLocation, checkPerson, checkDate,
        checkTime, checkDetail, checkDay, checkProof, checkCorrect
    )

    UpdateRepImageView(
        navController = navController,
        bitmap = repBitmap,
        viewModel = viewModel
    )
    Spacer(modifier = modifier.size(20.dp))

    Column(modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
        PostTitleBox(title = "카테고리 선택", approve = checkCategory) {
            Row {
                CategoryBoxList(state = category)
            }
        }

        PostTitleBox(title = "팟 제목", approve = checkTitle) {
            CustomTextField(
                placeholderText = "최대 15자",
                state = title, maxLength = 15
            )
        }


        PostTitleBox(
            modifier = modifier.onGloballyPositioned { coordinates ->
                scrollToPosition = coordinates.positionInRoot().y
            },
            title = "팟 상세정보", subTitle = "최대 5장 가능, 사진1부터 차례대로 표시돼요.",
            essential = false
        ) {
            UpdateSelectImageList(
                navController = navController,
                bitmapList = bodyBitmap,
                bitmapType = "BODY",
                viewModel = viewModel
            )
        }


        PostTitleBox(title = "위치정보", approve = checkLocation) {
            Column(
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                UpdateSelectLocationButtonList(
                    locationState = locationSelect,
                    viewModel = viewModel,
                    searchValue = locationSearchValue,
                    onSearchScreen = onSearchScreen,
                    searchPlaceResult = searchPlaceResult,
                    scrollState = scrollState,
                    scrollToPosition = scrollToPosition,
                    placeSelected = placeSelected
                )
            }
        }

        PostTitleBox(title = "팟 인원 설정", approve = checkPerson) {
            CustomTextField(
                placeholderText = "숫자만 입력해주세요. (최대 10,000명 가능)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                state = maxPerson,
                maxLength = 5,
            )
        }

        PostTitleBox(title = "시작일-종료일", approve = checkDate) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작일 선택", dateState = startDate,
                    formatter = convertDateFormat, widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { startDate.value = it },
                            onDismiss = { startPressed.value = !startPressed.value },
                        )
                    },
                    clickState = startPressed
                )
                Text(
                    "부터 시작",
                    modifier.padding(start = 8.dp, end = 10.dp),
                    style = Typography.bodySmall
                )
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료일 선택", dateState = endDate,
                    formatter = convertDateFormat, widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { endDate.value = it },
                            onDismiss = { endPressed.value = !endPressed.value },
                        )
                    },
                    clickState = endPressed
                )
                Text("에 종료", modifier.padding(start = 8.dp), style = Typography.bodySmall)
            }
        }

        PostTitleBox(title = "인증 가능 시간", approve = checkTime) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작시간", dateState = startTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            startPressed.value = !startPressed.value
                        }, timeState = startTime)
                    },
                    clickState = startPressed
                )
                Text(
                    "부터",
                    modifier.padding(start = 8.dp, end = 10.dp),
                    style = Typography.bodySmall
                )
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료시간", dateState = endTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            endPressed.value = !endPressed.value
                        }, timeState = endTime)
                    },
                    clickState = endPressed
                )
                Text("까지", modifier.padding(start = 8.dp), style = Typography.bodySmall)
            }
        }

        PostTitleBox(title = "팟 소개", approve = checkDetail) {
            CustomTextField(
                placeholderText = "최소 15자, 최대 500자",
                state = patDetail, maxLength = 500,
            )
        }

        PostTitleBox(title = "인증 빈도", approve = checkDay) {
            Row() {
                SelectDayButtonList(state = dayList)
            }
        }

        PostTitleBox(
            title = "인증방법 설명",
            approve = checkProof
        ) {
            CustomTextField(
                placeholderText = "최소 5자, 최대 30자",
                maxLength = 30,
                state = proofDetail
            )
        }

        PostTitleBox(
            title = "인증방법 예시",
            approve = checkCorrect
        ) {
            Row() {
                UpdateExampleImageView(
                    navController = navController,
                    text = "올바른 예시", backColor = GreenBack, textColor = GreenText,
                    bitmap = correctBitmap, bitmapType = "CORRECT",
                    viewModel = viewModel
                )
                Spacer(modifier = modifier.size(10.dp))
                UpdateExampleImageView(
                    navController = navController,
                    text = "잘못된 예시", backColor = RedBack, textColor = RedText,
                    bitmap = incorrectBitmap, bitmapType = "INCORRECT",
                    viewModel = viewModel
                )
            }
        }

        PostTitleBox(title = "인증 수단", subTitle = "실시간 촬영은 필수에요.", approve = true) {
            Row {
                CheckBoxView(text = "실시간 촬영")
                Spacer(modifier = modifier.size(12.dp))
                CheckBoxView(checked = isRealTime, text = "갤러리에서 사진 가져오기", isRealTime = true)
            }
        }

        if (checkList.all { it }) {
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
                        days = dayList.value,
                        category = category.value,
                        realtime = !isRealTime.value,
                    )
                    viewModel.clearImageData()
                    navController.popBackStack(
                        route = HOME,
                        inclusive = false
                    )
                })
        } else {
            FinalButton(
                text = "필수 항목을 모두 채워주세요", backColor = Gray300,
                textColor = White, stokeColor = Gray300,
            )
        }
    }
}
