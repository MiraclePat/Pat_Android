package com.pat.presentation.ui.post

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.common.CheckBoxView
import com.pat.presentation.ui.common.CustomTextField
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
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
                TimePicker(text = "시작일 선택")
                Spacer(modifier = modifier.padding(8.dp))
                Text("부터 시작", style = Typography.bodySmall)
                Spacer(modifier = modifier.padding(10.dp))
                TimePicker(text = "종료일 선택")
                Spacer(modifier = modifier.padding(8.dp))
                Text("에 종료", style = Typography.bodySmall)
            }
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 가능 시간", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                TimePicker(text = "시작시간")
                Spacer(modifier = modifier.padding(8.dp))
                Text("부터", style = Typography.bodySmall)
                Spacer(modifier = modifier.padding(10.dp))
                TimePicker(text = "종료시간")
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

            FinalButton(text = "확정", onClick = {})
        }
    }
}

@Composable
fun TimePicker(modifier: Modifier = Modifier, text: String) {
    val widthSize = if (text.length == 4) 81.dp else 96.dp

    Box(
        modifier = modifier
            .width(widthSize)
            .height(36.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(1.dp, color = Gray300, shape = RoundedCornerShape(100.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.labelMedium,
            color = Gray600,
        )
    }
}

@Composable
fun SelectImage(modifier: Modifier = Modifier, imageIdx: Int = -1) {
    val roundedCornerShape = if (imageIdx == -1) RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp
    ) else RoundedCornerShape(4.dp)
    Box(
        modifier
            .height(140.dp)
            .width(130.dp)
            .clip(roundedCornerShape)
            .background(Gray100)
            .clickable {
                // TODO click Event
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = Gray500
            )
            Box() {
                if (imageIdx == -1) Text("사진 첨부하기", style = Typography.labelSmall)
                else Text("사진$imageIdx 첨부하기", style = Typography.labelSmall)
            }
        }
    }
}

@Composable
fun SelectImageList(modifier: Modifier = Modifier) {
    val imageList = listOf<Int>(1, 2, 3, 4, 5)

    LazyRow() {
        items(imageList) { imageIdx ->
            SelectImage(imageIdx = imageIdx)
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
        }
    }
}

@Composable
fun ExampleImageView(
    modifier: Modifier = Modifier,
    backColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit = {}
) {
    Column(modifier.clickable {
        onClick
    }) {
        SelectImage()
        Box(
            modifier
                .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                .background(backColor)
                .height(26.dp)
                .width(130.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = Typography.labelMedium,
                fontSize = 12.sp,
                color = textColor
            )
        }
    }
}

