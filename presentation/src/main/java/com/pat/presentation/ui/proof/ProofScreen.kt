package com.pat.presentation.ui.proof

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.proof.ProofContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.DayButtonList
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.IconWithTextView
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.common.convertDateViewFormat
import com.pat.presentation.ui.common.convertTimeViewFormat
import com.pat.presentation.ui.common.setUnderLine
import com.pat.presentation.ui.pat.DateText
import com.pat.presentation.ui.pat.ProofImageView
import com.pat.presentation.ui.theme.FailCircleColor
import com.pat.presentation.ui.theme.FailTextColor
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray50
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Gray900
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RemainColor
import com.pat.presentation.ui.theme.SuccessCircleColor
import com.pat.presentation.ui.theme.SuccessTextColor
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProofScreenView(
    modifier: Modifier = Modifier,
    navController: NavController,
    proofViewModel: ProofViewModel
) {
    val uiState by proofViewModel.uiState.collectAsState()
    val proofState by proofViewModel.proofs.collectAsState()
    LaunchedEffect(uiState.content) {
        proofViewModel.getMyProof()
    }
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "팟 상세 페이지",
                        style = Typography.labelMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
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
        ) {
            if (uiState.content != null) {
                ProofScreen(
                    content = uiState.content!!,
                    viewModel = proofViewModel,
                    navController = navController,
                    proofContents = proofState.content
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProofScreen(
    modifier: Modifier = Modifier,
    viewModel: ProofViewModel,
    content: ParticipatingDetailContent,
    navController: NavController,
    proofContents: List<ProofContent>?
) {
    var spreadState by remember { mutableStateOf(false) }
    var myProofState by remember { mutableStateOf(true) }
    val viewStartTime = convertTimeViewFormat(content.startTime)
    val viewEndTime = convertTimeViewFormat(content.endTime)
    val viewStartDate = convertDateViewFormat(content.startDate)
    val viewEndDate = convertDateViewFormat(content.endDate)
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val proofBitmap by viewModel.proofBitmap.collectAsState() //인증사진
    val bottomSheetState by viewModel.bottomSheetState.collectAsState()



    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
//            CategoryBox(
//                category = content.category,
//                isSelected = true
//            )
            Spacer(modifier = modifier.padding(horizontal = 8.dp))
            Text(
                style = Typography.displayLarge,
                text = content.patName,
                fontSize = 20.sp,
                color = Gray900,
                modifier = modifier.padding(3.dp)
            )
            Spacer(modifier.weight(1f))
            Row(modifier.clickable {
                navController.navigate("")
            }) {
                Text(
                    style = Typography.displaySmall,
                    text = "모집 공고로 이동",
                    color = Gray600,
                    modifier = modifier.padding(3.dp)
                )
                Icon(
                    modifier = modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_caret_right
                    ),
                    contentDescription = null,
                    tint = Gray600
                )
            }
        }
        Spacer(modifier.height(16.dp))
        Column(
            modifier
                .fillMaxWidth()
                .height(75.dp)
                .border(1.dp, Gray300, RoundedCornerShape(6.dp))
                .padding(horizontal = 12.dp, vertical = 14.dp)
        ) {
            Row() {
                SimpleTextView(
                    text = content.location,
                    vectorResource = R.drawable.ic_map,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
                Spacer(
                    modifier = modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                )
                SimpleTextView(
                    text = "$viewStartDate - $viewEndDate",
                    vectorResource = R.drawable.ic_calendar,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
            }
            Spacer(modifier = modifier.padding(bottom = 12.dp))
            Row() {
                SimpleTextView(
                    text = "$viewStartTime - $viewEndTime",
                    vectorResource = R.drawable.ic_alram,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
                Spacer(modifier = modifier.padding(horizontal = 8.dp))
                SimpleTextView(
                    text = "${content.days} 인증",
                    vectorResource = R.drawable.na_circle_check,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
            }
        }
        Spacer(modifier.padding(bottom = 20.dp))
        Row(modifier.clickable {
            spreadState = !spreadState
        }, verticalAlignment = Alignment.CenterVertically) {
            Text("팟 상세정보 펼치기", style = Typography.titleLarge, color = Gray800)
            Spacer(modifier.weight(1f))
            Icon(
                modifier = modifier.size(24.dp),
                imageVector = ImageVector.vectorResource(
                    id = if (!spreadState) R.drawable.ic_caret_down else R.drawable.ic_caret_up_sm
                ),
                contentDescription = null,
                tint = Gray800
            )
        }

        Spacer(modifier = modifier.padding(top = 18.5.dp))
        Box(
            modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Gray50)
        )
        if (spreadState) {
            Column(modifier.padding(top = 24.dp)) {
                Text("팟 소개", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                IconWithTextView(
                    "팟 디테일이 빠졌어요!",
                    iconResource = R.drawable.ic_chat_dot
                )
                Spacer(modifier.padding(bottom = 28.dp))

                Text("위치 정보", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                IconWithTextView(
                    content.location,
                    iconResource = R.drawable.ic_map
                )
                Spacer(modifier.padding(bottom = 28.dp))

                Text("시작일 - 종료일", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                DateText(
                    startDate = viewStartDate,
                    endDate = viewEndDate,
                    iconResource = R.drawable.ic_calendar
                )
                Spacer(modifier.padding(bottom = 28.dp))

                Text("인증 빈도 / 인증 빈도 더미테이더 연결 필요합니다", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                DayButtonList()
                Spacer(modifier.padding(bottom = 28.dp))

                Text("인증 가능시간", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                IconWithTextView(
                    "${viewStartTime}부터 ${viewEndTime}까지",
                    iconResource = R.drawable.ic_alram
                )
                Spacer(modifier.padding(bottom = 24.dp))
                Box(
                    modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(Gray50)
                )
            }
        }

        Spacer(modifier.padding(bottom = 24.dp))
        Text("인증 방법", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.padding(bottom = 14.dp))
        IconWithTextView(
            content.proofDetail,
            iconResource = R.drawable.ic_chat_check
        )
        Spacer(modifier.padding(bottom = 14.dp))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            ExampleImageView(
//                text = "올바른 예시",
//                backColor = GreenBack,
//                textColor = GreenText,
//                hasSource = "여기에 사진 uri 추가"
//            )
//            Spacer(modifier = modifier.size(10.dp))
//            ExampleImageView(
//                text = "잘못된 예시",
//                backColor = RedBack,
//                textColor = RedText,
//                hasSource = "여기에 uri 추가"
//            )
        }
        Spacer(modifier = modifier.padding(top = 28.dp))
        Box(
            modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Gray50)
        )
        Row(modifier = modifier.padding(top = 24.dp)) {

            Box(modifier.clickable {
                myProofState = true
                viewModel.getMyProof()
            }) {
                Text(
                    modifier = if (myProofState) setUnderLine else modifier,
                    text = "내 인증 현황",
                    style = Typography.titleLarge,
                    color = if (myProofState) Gray800 else Gray500
                )

            }
            Spacer(modifier = modifier.padding(end = 14.dp))
            Box(modifier.clickable {
                myProofState = false
                viewModel.getSomeoneProof()
            }) {
                Text(
                    modifier = if (!myProofState) setUnderLine else modifier,
                    text = "다른 참여자 인증",
                    style = Typography.titleLarge,
                    color = if (!myProofState) Gray800 else Gray500
                )
            }
        }
        Spacer(modifier = modifier.padding(bottom = 24.dp))
        if (myProofState) {
            ProofStatus(
                success = content.myProof,
                fail = content.myFailProof,
                all = content.maxProof,
                imgUriList = proofContents
            )
        } else {
            ProofStatus(
                success = content.allProof,
                fail = content.allFailProof,
                all = content.allMaxProof,
                imgUriList = proofContents
            )
        }
        FinalButton(text = "인증하기", onClick = { showBottomSheet = true })
        if (showBottomSheet || bottomSheetState) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    viewModel.clearBitmap()
                },
                sheetState = sheetState,
                modifier = modifier.height(320.dp)
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "인증할 사진을 첨부해주세요!",
                        style = Typography.labelMedium,
                        color = Gray800,
                    )

                    Spacer(modifier.padding(bottom = 16.dp))

                    ProofImageView(
                        navController = navController,
                        bitmap = proofBitmap,
                        viewModel = viewModel,
                        realTime = true
                    )

                    Spacer(modifier.padding(bottom = 32.dp))


                    SelectButton(
                        text = "인증하기",
                        onClick = {
//                            val proofImageByte = viewModel.
//                            viewModel.proofPat()
                        },
                        backColor = if (proofBitmap == null) Gray300 else PrimaryMain,
                        textColor = if (proofBitmap == null) White else White,
                        cornerSize = 100.dp,
                        stokeColor = if (proofBitmap == null) Gray300 else PrimaryMain,
                        stokeWidth = 1.dp
                    )

                    Spacer(modifier.padding(bottom = 16.dp))

                }
            }
        }
    }
}

@Composable
fun ProofStatus(
    modifier: Modifier = Modifier,
    success: Int,
    fail: Int,
    all: Int,
    isAll: String = "",
    imgUriList: List<ProofContent>?
) {
    val title = if (isAll == "") "나의 인증사진" else "참여자들의 인증사진"
    Row() {
        Text("${isAll}인증 성공 횟수", style = Typography.titleLarge, color = Gray600, fontSize = 14.sp)
        Spacer(modifier = modifier.weight(1f))
        Text("${success}회 성공", style = Typography.titleLarge, color = PrimaryMain, fontSize = 18.sp)
        Spacer(modifier = modifier.padding(end = 5.dp))
        Text("/ 총 ${all}회", style = Typography.labelSmall, color = Gray600, fontSize = 14.sp)
    }
    Row(modifier = modifier.padding(top = 12.dp)) {
        Text("${isAll}인증 실패 횟수", style = Typography.titleLarge, color = Gray600, fontSize = 14.sp)
        Spacer(modifier = modifier.weight(1f))
        Text("${fail}회 실패", style = Typography.titleLarge, color = Gray600, fontSize = 18.sp)
        Spacer(modifier = modifier.padding(end = 5.dp))
        Text("/ 총 ${all}회", style = Typography.labelSmall, color = Gray600, fontSize = 14.sp)
    }
    Spacer(modifier = modifier.padding(bottom = 28.dp))
    Text(title, style = Typography.titleLarge, color = Gray800)

    // TODO 연동 시 lazyRow로 수정
    if (imgUriList.isNullOrEmpty()) {
        Box(modifier.padding(top = 12.dp).size(140.dp)) {
            Text("인증 내역이 없어요!", style = Typography.titleLarge, color = Gray800)
        }
    } else {
        LazyRow(modifier.padding(top = 12.dp)) {
            items(imgUriList) {img ->
                GlideImage(
                    modifier = modifier
                        .size(130.dp, 140.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    imageModel = { img.proofImg })
                Spacer(modifier = modifier.padding(end = 10.dp))
            }
        }
    }
    Row(modifier.padding(top = 24.dp)) {
        RatioText(
            text = "성공률",
            textColor = SuccessTextColor,
            circleColor = SuccessCircleColor,
            percentage = (success / all.toFloat() * 100).roundToInt()
        )
        Spacer(modifier = modifier.padding(end = 12.dp))
        RatioText(
            text = "실패율",
            textColor = FailTextColor,
            circleColor = FailCircleColor,
            percentage = (fail / all.toFloat() * 100).roundToInt()
        )
        Spacer(modifier = modifier.padding(end = 12.dp))
        RatioText(
            text = "남은 달성률",
            textColor = Gray700,
            circleColor = RemainColor,
            percentage = ((all - success - fail) / all.toFloat() * 100).roundToInt()
        )
    }
    PercentBar(success = success, fail = fail, all = all)
    Spacer(modifier = modifier.padding(bottom = 36.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PercentBar(modifier: Modifier = Modifier, success: Int, fail: Int, all: Int) {
    FlowRow(
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier
                .fillMaxWidth(success / all.toFloat())
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 100.dp, bottomStart = 100.dp))
                .background(SuccessCircleColor)
        )
        Box(
            modifier
                .fillMaxWidth(fail / all.toFloat())
                .fillMaxHeight()
                .background(FailCircleColor)
        )
        Box(
            modifier
                .fillMaxWidth((all - success - fail) / all.toFloat())
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp))
                .background(Gray200)
        )
    }
}

@Composable
fun RatioText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    circleColor: Color,
    percentage: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(circleColor)
        )
        Spacer(modifier = modifier.padding(end = 6.dp))
        Text(text = text, style = Typography.labelSmall, fontSize = 14.sp, color = Gray700)
        Spacer(modifier = modifier.padding(end = 6.dp))
        Text(
            text = "$percentage%",
            style = Typography.labelSmall,
            fontSize = 14.sp,
            color = textColor
        )
    }
}