package com.pat.presentation.ui.post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.home.components.HomeCategory
import com.pat.presentation.ui.home.components.HomeMyPat
import com.pat.presentation.ui.home.components.HomePats
import com.pat.presentation.ui.home.components.Pats
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.skydoves.landscapist.glide.GlideImage

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
            SelectImageView()
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
            // TODO 사진 첨부하기
            Spacer(modifier = modifier.size(36.dp))

            Text(text = "인증 수단", style = Typography.titleLarge)
            Spacer(modifier = modifier.size(14.dp))
            // TODO 체크 박스
            Spacer(modifier = modifier.size(36.dp))
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
            .clickable {  },
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
fun SelectImageView(modifier: Modifier = Modifier) {
    @Composable
    fun SelectImage(imageIdx: String) {
        Box(
            modifier
                .height(140.dp)
                .width(130.dp)
                .background(Gray200)
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
                    Text("$imageIdx 첨부하기", style = Typography.labelSmall)
                }
            }
        }
    }

    val imageList = listOf<String>("사진1", "사진2", "사진3", "사진4", "사진5")

    LazyRow() {
        items(imageList) {
            SelectImage(it)
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
        }

    }
}